package com.example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

  private final ProductService service;

  public AppController(ProductService service) {
    this.service = service;
  }

  @RequestMapping("/")
  public String viewHomePage(Model model) {
    List<Product> productList = service.listAll();
    System.out.println("List" + productList);
    model.addAttribute("listProducts", productList);
    return "index";
  }

  @RequestMapping("/new")
  public String showNewProductPage(Model model) {
    model.addAttribute(new Product());
    return "new_product";
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String saveProduct(@ModelAttribute("product") Product product) {
    service.save(product);
    return "redirect:/";
  }

  //  @RequestMapping("/edit/{id}")
  //  public String showEditProductPage(@PathVariable("id") Long id, Model model){
  //    Product product = service.get(id);
  //    model.addAttribute("product",product);
  //    return "edit_product";
  //  }

  @RequestMapping("/edit/{id}")
  public ModelAndView showEditProductPage(@PathVariable("id") Long id) {
    Product product = service.get(id);
    ModelAndView modelAndView = new ModelAndView("edit_product");
    modelAndView.addObject(product);
    return modelAndView;
  }

  @RequestMapping("/delete/{id}")
  public String deleteProduct(@PathVariable(name = "id") Long id) {
    service.delete(id);
    return "redirect:/";
  }

  @RequestMapping("/reflect/test")
  public String reflect() {
    try {
      Class<?> clazz = Class.forName("com.example.Product");

      Field[] fields = clazz.getFields();

      for (Field field : fields) {
        System.out.println("Field :" + field.getName() + ", Type: " + field.getType());
      }

      Field field = clazz.getDeclaredField("id");

      field.setAccessible(true);

      System.out.println(field);

      Method[] methods = clazz.getDeclaredMethods();
      for (Method method : methods) {
        System.out.println(
            "Method: " + method.getName() + ", Return Type: " + method.getReturnType());
      }
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }

    return "index";
  }
}
