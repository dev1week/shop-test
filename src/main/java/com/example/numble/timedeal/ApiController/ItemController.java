package com.example.numble.timedeal.ApiController;
import com.example.numble.timedeal.Dto.ItemDto.ItemDto;
import com.example.numble.timedeal.domain.RoleType;
import com.example.numble.timedeal.constance.SessionConst;
import com.example.numble.timedeal.domain.Member;
import com.example.numble.timedeal.domain.item.Item;
import com.example.numble.timedeal.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController()
public class ItemController {

    @Autowired
    ItemRepository itemRepository;


    @GetMapping("items/{itemId}")
    public ResponseEntity<ItemDto> getItemInfo(@PathVariable("itemId") final long itemId){

        Item foundItem = itemRepository.findById(itemId).get();

        if(foundItem == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ItemDto itemDto = ItemDto.from(foundItem);
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @PostMapping("items/add")
    public ResponseEntity<Item> addItem(@RequestBody ItemDto itemDto, HttpServletRequest request){
        log.info("상품등록 컨트롤러가 호출되었습니다.");
        HttpSession session= request.getSession(false);
        //로그인을 먼저해야한다.
        if(session == null){
            log.info("세션이 없습니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //서버가 가지고 있지 않은 세션은 거절한다.
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(loginMember == null){
            log.info("서버와 동일한 세션이 없습니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //사용자가 어드민일 경우에만 등록가능하다.
        if(loginMember.getRoleType()==RoleType.ADMIN){
            Item addItem = itemDto.toEntity();
            Item savedItem = itemRepository.save(addItem);
            log.info("{ } 성공적으로 등록했습니다.", savedItem);
            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        }else{
            log.info("권리자 계정만 가지고 있습니다");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
