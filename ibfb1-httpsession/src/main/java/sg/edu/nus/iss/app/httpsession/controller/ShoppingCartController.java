package sg.edu.nus.iss.app.httpsession.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.app.httpsession.models.Cart;
import sg.edu.nus.iss.app.httpsession.models.Item;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping(path="/cart")
public class ShoppingCartController {
    
    @GetMapping
    public String getCart(Model model, HttpSession session){
        Cart cart = (Cart)session.getAttribute("cart");
        if(null == cart){
            cart = new Cart();
            session.setAttribute("cart",cart);
        }

        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);
        
        return "cart";
    }

    @PostMapping()
    public String postData(Model model, HttpSession session,
        @Valid Item item, BindingResult bindResult) {
        Cart cart = (Cart)session.getAttribute("cart");
        
        if(bindResult.hasErrors()){
            model.addAttribute("item", item);
            model.addAttribute("cart", cart);
            return "cart";
        }

        cart.addItemToCart(item);
        model.addAttribute("item", item);
        model.addAttribute("cart", cart);
        return "cart";
    }
    
}
