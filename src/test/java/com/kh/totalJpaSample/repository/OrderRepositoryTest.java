package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.constant.ItemSellStatus;
import com.kh.totalJpaSample.entity.Item;
import com.kh.totalJpaSample.entity.Member;
import com.kh.totalJpaSample.entity.Order;
import com.kh.totalJpaSample.entity.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest // 테스트라서 자동 롤백 기능이 있음 DB에 저장이 안되고 자체 기록이 안남음. 마지막에 자동으로 롤백됨
@Transactional // DB의 논리적인 작업 단위, 모두 성공한게 아니면 롤벡시킴
@Slf4j
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderRepositoryTest {
    // 의존성 주입
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;
    @Autowired
    OrderItemRepository orderItemRepository;

    public Item createItem() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return item;
    }
    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);     // 부모 수량
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }
        // 엔티티를 저장하면서 DB에 반영
        orderRepository.saveAndFlush(order);// 만들자마자 저장하고 테스트가 이어져야 하면 saveAndFlush해야함. 아닌경우는 그냥 save만.
        em.clear();     // 영속성 상태를 초기화

        // 주문 엔티티 조회
        Order saveOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new); // 새로운걸 만들어서 반환

        // Order 객체의 OrderItem 개수가 3개인지 확인 → 영속성 전이를 안넣으면, 리스트에 값이 0으로 나옴
        log.warn(String.valueOf(saveOrder.getOrderItemList().size())); // log로 해야 돌렸을 때 list에 3개 담겼다고 뜸

    }

    public Order createOrder() {
        Order order = new Order();
        for(int i = 0; i < 3; i++) {
            Item item = createItem(); // createItem을 3번 부르게 된 거니, item이 3개 만들어짐.
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }
        Member member = new Member();
        member.setName("곰돌이사육사");
        member.setEmail("jks2024@gmail.com");
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }
    @Test
    @DisplayName("고아 객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = this.createOrder();
        order.getOrderItemList().remove(0); // 배열을 지운다고 DB에서 지워지는건 아니지만 고아객체를 지울 수 있음
        em.flush();
    }
    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItemList().get(0).getId();
        em.flush();
        em.clear();
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
        log.warn(String.valueOf(orderItem.getOrder().getClass()));
        log.warn("============================================================");
        log.warn(String.valueOf(orderItem.getOrder().getOrderDate()));
        log.warn("============================================================");
    }

}