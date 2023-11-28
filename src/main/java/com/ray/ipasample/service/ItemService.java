package com.ray.ipasample.service;

import com.ray.ipasample.Repository.ItemRepository;
import com.ray.ipasample.domain.item.Book;
import com.ray.ipasample.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final이 있는 필드만 생성자를 만들어 줌
public class ItemService {

//    @Autowired // 필드 주입 방식 (스프링 컨테이너에 등록되어 있는 ItemRepository 빈을 찾아 등록 됨)
    private final ItemRepository itemRepository;

    // @RequiredArgsConstructor를 사용하여 생략 가능
//    @Autowired // 생성자를 통한  Inject 방식 (필드 주입 방식 장점 + setter를 통한 주입 방식 장점 혼용
//    public ItemService(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

//    @Autowired // setter를 통한 주입방식(유용한 의존성 관리(객체 생성 이후에도 변경가능, 선택적 의존성, 단위테스트 용이)
//    public void setItemRepository(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//
//        // 개발 이후에는 setter Injection 방식을 고집할 이유가 없다

//    }
    @Transactional(readOnly = false)
    public void saveItem(Item item) {
        this.itemRepository.save(item);
    }

    public Item findOne(Long id) {
        return this.itemRepository.findOne(id);
    }

    public List<Item> findAll() {
        return this.itemRepository.findAll();
    }

    // 변경감지 기법을 이용한 상품 수정
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity, String author, String isbn) {
        Book item = (Book) this.itemRepository.findOne(itemId); // update될 item을 select 해옴

        // item(메모리에 있는)의 값을 setter로 변경
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        item.setAuthor(author);
        item.setIsbn(isbn);

        // 현재 트랜젝션이 종료될때 영속성 객체(item)의 값이 변경되어진 것을 감지하면 update 쿼리문을 날려 update한다. -> 변경감지(Dirty Checking)
    }
}
