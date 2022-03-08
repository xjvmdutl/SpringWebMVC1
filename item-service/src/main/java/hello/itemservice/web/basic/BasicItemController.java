package hello.itemservice.web.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    //@Autowired //생성자가 하나라서 생략 가능
    /*
    public BasicItemController(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }
     */

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(
        @RequestParam String itemName,
        @RequestParam int price,
        @RequestParam Integer quantity,
        Model model
    ) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2(
        @ModelAttribute("item") Item item
        //, Model model
    ) {
        //지정한 이름으로 모델에 담아 뷰에 반환한다.
        itemRepository.save(item);
        //model.addAttribute("item", item); //자동 추가, 생략 가능
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(
        @ModelAttribute Item item 
    ) {
        //속성의 이름을 지우게 된다면 클래스 이름의 첫글자만 소문자로 바꾼 이름으로 넣어준다
        itemRepository.save(item);
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV4(
        Item item //@ModelAttribute 생략 가능
    ) {
        itemRepository.save(item);
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV5(
        Item item
    ) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId(); //URL 인코딩이 되지 않기 떄문에 인코딩을 해서 넘겨야한다.
    }

    @PostMapping("/add")
    public String addItemV6(
        Item item, RedirectAttributes redirectAttributes
    ) {
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}"; //치환되지 못한 값들은 쿼리파람으로 전달된다
    }

   @GetMapping("/{itemId}/edit")
    public String editForm(
        @PathVariable Long itemId, Model model
    ) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(
        @PathVariable Long itemId, @ModelAttribute Item item
    ) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
