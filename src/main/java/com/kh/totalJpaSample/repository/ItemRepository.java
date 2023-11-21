package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository                     // 원래는 붙여줘야 맞음, 근데 없어도 가능함
// JpaRepository로부터 상속받음
// 기본적인 CRUD는 JpaRepository에 이미 정의되어 있음, 페이징 처리도 포함되어 있음
public interface ItemRepository extends JpaRepository<Item, Long> {
// 변수 이름은 item entity에서 선언한대로 써야함
    List<Item> findByItemName(String itemName);

    // or조건 처리
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);

    // LessThan 조건처리 : '~~보다 작다' 특정 가격보다 저렴한 상품을 조회하는 쿼리
    List<Item> findByPriceLessThan(Integer price);

    // OrderBy로 정렬하기
    List<Item> findAllByOrderByPriceDesc();

    // JPQL 쿼리 작성하기 : SQL 유사한 객체지향 쿼리 언어
    // DB쪽으로 날리는게 아니기 때문에 (엔티티쪽으로 날림) 컬럼명을 쓰지 않음
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> priceSorting(@Param("itemDetail") String itemDetail);

    // nativeQuery 사용하기
    // 실제 DB에 쿼리를 날릴 수 있음
    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> priceSortingNative(@Param("itemDetail") String itemDetail);
}
