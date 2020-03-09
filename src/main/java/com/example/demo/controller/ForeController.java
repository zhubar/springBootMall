package com.example.demo.controller;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.pojo.*;
import com.example.demo.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    UserService userService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;

    @RequestMapping("/testLogin")
    public String testLogin(HttpServletRequest request, Model m, Integer page) throws Exception{
        request.setAttribute("msg", "good");
        return "/login";
    }


    @RequestMapping("/home")
    public String home(HttpServletRequest request, Model m, Integer page) throws Exception {
        if(page == null){
            page = 1;
        }
        int pageSize =15;
        PageHelper.startPage(page,pageSize);
        List<Category> cs =categoryService.findAll();
        for(Category c :cs){
            List<Product>ps = productService.findByCid(c.getId());
            for(Product p : ps){
                p.setFirstProductImage(productImageService.findSingleByPid(p.getId()).get(0));
            }
            c.setProducts(ps);
        }
        int productNumberEachRow = 8;
        for (Category c : cs) {
            List<Product> products = c.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            c.setProductsByRow(productsByRow);
        }
        m.addAttribute("categories", cs);

        return "/home.html";
    }

    @RequestMapping("/userLogin")
    public String userLogin(HttpServletRequest request, Model m, Integer page) throws Exception{
        return "/login.html";
    }

    @RequestMapping("/userInformation")
    public String userInformation(HttpServletRequest request, Model m, Integer page) throws Exception{
        return "/userInformation.html";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model m, Integer page) throws Exception{
        String name = request.getParameter("name");
        name = HtmlUtils.htmlEscape(name);
        String password = request.getParameter("password");
        User user = userService.findByNameAndPassword(name,password);
        if (null == user) {
            request.setAttribute("msg", "password error");
            return "/login.html";
        }
        request.getSession().setAttribute("user", user);
        return "redirect:/home";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, Model m, Integer page) throws Exception{
        request.getSession().removeAttribute("user");
        return "redirect:/home";
    }

    @RequestMapping("/userRegister")
    public String userRegister(HttpServletRequest request, Model m, Integer page) throws Exception{
        return "/register.html";
    }

    @RequestMapping("/modifySuccess")
    public String modifySuccess(HttpServletRequest request, Model m, Integer page) throws Exception{
        return "/modifySuccess.html";
    }

    @RequestMapping("/register")
    public String register(HttpServletRequest request, Model m, Integer page) throws Exception{
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        name = HtmlUtils.htmlEscape(name);
        System.out.println(name);
        User u =new User();
        u = userService.findByName(name);

        if ( u != null) {
            request.setAttribute("msg", "username has registered");
            return "/register.html";
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userService.insert(user);

        return "/registerSuccess.html";
    }

    @RequestMapping("/modify")
    public String modify(HttpServletRequest request, Model m, Integer page) throws Exception{
        String address = request.getParameter("address");
        String mobile = request.getParameter("mobile");
        String receiver= request.getParameter("receiver");
        String password= request.getParameter("password");

        User user = (User)request.getSession().getAttribute("user");
        user.setMobile(mobile);
        user.setReceiver(receiver);
        user.setAddress(address);
        user.setPassword(password);
        userService.update(user);
        return "/modifySuccess.html";
    }

    @RequestMapping("/product")
    public String product(HttpServletRequest request, Model m, Integer page) throws Exception{
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p = productService.findById(pid);
        Category c = categoryService.findById(p.getCid());

        List<ProductImage> productSingleImages = productImageService.findSingleByPid(p.getId());
        List<ProductImage> productDetailImages = productImageService.findDetailByPid(p.getId());
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        p.setFirstProductImage(productSingleImages.get(0));

        List<PropertyValue> pvs = propertyValueService.findByPid(p.getId());
        List<Review> reviews = reviewService.findByPid(p.getId());
        List<OrderItem> orderItems = orderItemService.findByPid(p.getId());
        int sell = 0;
        for(OrderItem o : orderItems){
            if(o.getOid()>0)
                sell += o.getNumber();
        }
        p.setSell(sell);
        p.setReviewCount(reviews.size());

        request.setAttribute("reviews", reviews);

        request.setAttribute("p", p);
        request.setAttribute("c", c);
        request.setAttribute("pvs", pvs);
        return "/product.html";
    }

    @RequestMapping("/checkLogin")
    @ResponseBody
    public String checkLogin(HttpServletRequest request, Model m, Integer page) throws Exception{
        User user = (User) request.getSession().getAttribute("user");
        if (null != user)
            return "success";
        return "fail";
    }

    @RequestMapping("/loginAjax")
    @ResponseBody
    public String loginAjax(HttpServletRequest request, HttpServletResponse response, Integer page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user = userService.findByNameAndPassword(name,password);

        if (null == user) {
            return "fail";
        }
        request.getSession().setAttribute("user", user);
        return "success";
    }
    @RequestMapping("/category")
    public String category(HttpServletRequest request, HttpServletResponse response, Integer page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryService.findById(cid);
        List<Product>ps = productService.findByCid(cid);
        for(Product p : ps){
            List<Review> reviews = reviewService.findByPid(p.getId());
            List<OrderItem> orderItems = orderItemService.findByPid(p.getId());
            List<ProductImage> pis = productImageService.findSingleByPid(p.getId());
            p.setFirstProductImage(pis.get(0));
            int sell = 0;
            for(OrderItem o : orderItems){
                if(o.getOid()>0)
                    sell += o.getNumber();
            }
            p.setSell(sell);
            p.setReviewCount(reviews.size());
        }
        category.setProducts(ps);

        String sort = request.getParameter("sort");
        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getReviewCount() - p1.getReviewCount();
                        }
                    });
                    break;
                case "date":
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p1.getCreateDate().compareTo(p2.getCreateDate());
                        }
                    });
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p1.getSaleCount() - p2.getSaleCount();
                        }
                    });
                    break;
                case "price":
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return (int) (p1.getPromotePrice() - p2.getPromotePrice());
                        }
                    });
                    break;
                case "all":
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount();
                        }
                    });
                    break;

            }
        }

        request.setAttribute("category", category);
        return "/category.html";
    }
    @RequestMapping("/search")
    public String search(HttpServletRequest request, Model m, HttpServletResponse response, Integer page) {
        String keyword = request.getParameter("keyword");
        if(page == null){
            page = 1;
        }
        int pageSize = 20;
        PageHelper.startPage(page,pageSize);
        List<Product> ps= productService.search(keyword);
        String sort = request.getParameter("sort");
        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(ps, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getReviewCount() - p1.getReviewCount();
                        }
                    });
                    break;

                case "saleCount":
                    Collections.sort(ps, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p1.getSaleCount() - p2.getSaleCount();
                        }
                    });
                    break;
                case "price":
                    Collections.sort(ps, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return (int) (p1.getPromotePrice() - p2.getPromotePrice());
                        }
                    });
                    break;
                case "all":
                    Collections.sort(ps, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount();
                        }
                    });
                    break;

            }
        }
        try{
            request.setCharacterEncoding("UTF-8");
        }
        catch(UnsupportedEncodingException e) {

        }
        //request.setAttribute("keyword",keyword);
        //m.addAttribute("keyword",keyword);
        //m.addAttribute("ps",ps);
        request.setAttribute("ps", ps);
        request.getSession().setAttribute("keyword", keyword);
        return "/searchResult.html";
    }
    @RequestMapping("/buyone")
    public String buyone(HttpServletRequest request, HttpServletResponse response, Integer page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int num = Integer.parseInt(request.getParameter("num"));
        Product p = productService.findById(pid);
        int orderItemId = 0;

        User user = (User) request.getSession().getAttribute("user");
        OrderItem orderItem = new OrderItem();
        orderItem.setUser(user);
        orderItem.setUid(user.getId());
        orderItem.setNumber(num);
        orderItem.setProduct(p);
        orderItem.setPid(p.getId());
        orderItem.setOid(0);
        orderItemService.insert(orderItem);
        orderItemId = orderItem.getId();
        return "redirect:/buy?orderItemId=" + orderItemId;
    }

    @RequestMapping("/buy")
    public String buy(HttpServletRequest request, HttpServletResponse response, Integer page) {
        String[] oiids = request.getParameterValues("orderItemId");
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for (String strid : oiids) {
            int orderItemId = Integer.parseInt(strid);
            OrderItem orderItem = orderItemService.findById(orderItemId);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
            orderItems.add(orderItem);
        }
        request.getSession().setAttribute("orderItems", orderItems);
        //request.setAttribute("orderItems", orderItems);
        request.setAttribute("total", total);
        return "/buy.html";
    }

    @RequestMapping("/addCart")
    @ResponseBody
    public String addCart(HttpServletRequest request, HttpServletResponse response, Integer page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productService.findById(pid);
        int num = Integer.parseInt(request.getParameter("num"));

        User user = (User) request.getSession().getAttribute("user");
        boolean found = false;

        List<OrderItem> orderItems = orderItemService.findByUid(user.getId());
        for (OrderItem orderItem : orderItems) {
            if(orderItem.getOid()==0) {
                Product p = productService.findById(orderItem.getPid());
                orderItem.setProduct(p);
                if (orderItem.getProduct().getId() == product.getId()) {
                    orderItem.setNumber(orderItem.getNumber() + num);
                    orderItemService.update(orderItem);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUser(user);
            orderItem.setUid(user.getId());
            orderItem.setNumber(num);
            orderItem.setProduct(product);
            orderItem.setPid(product.getId());
            orderItem.setOid(0);
            orderItemService.insert(orderItem);
        }
        return "success";
    }

    @RequestMapping("/cart")
    public String cart(HttpServletRequest request, HttpServletResponse response, Integer page) {
        User user = (User) request.getSession().getAttribute("user");
        List<OrderItem> orderItems = orderItemService.findByUid(user.getId());
        for (OrderItem orderItem : orderItems) {
            Product p = productService.findById(orderItem.getPid());
            List<ProductImage>pis = productImageService.findSingleByPid(p.getId());
            p.setFirstProductImage(pis.get(0));
            orderItem.setProduct(p);

        }
        request.setAttribute("orderItems", orderItems);
        return "/cart.html";
    }

    @RequestMapping("/changeOrderItem")
    @ResponseBody
    public String changeOrderItem(HttpServletRequest request, HttpServletResponse response,Integer page) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            return "fail";
        }
        int pid = Integer.parseInt(request.getParameter("pid"));
        int number = Integer.parseInt(request.getParameter("number"));
        List<OrderItem> orderItems = orderItemService.findByUid(user.getId());
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == pid) {
                orderItem.setNumber(number);
                orderItemService.update(orderItem);
                break;
            }
        }
        return "success";
    }

    @RequestMapping("/deleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Integer page) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            return "fail";
        }
        int orderItemId = Integer.parseInt(request.getParameter("orderItemId"));
        orderItemService.delete(orderItemId);
        return "success";
    }

    @RequestMapping("/createOrder")
    public String createOrder(HttpServletRequest request, HttpServletResponse response, Integer page) {
        User user = (User) request.getSession().getAttribute("user");
        List<OrderItem> orderItems = (List<OrderItem>) request.getSession().getAttribute("orderItems");
        if (orderItems.isEmpty()) {
            return "redirect:/login.html";
        }
        String address = request.getParameter("address");
        String receiver = request.getParameter("receiver");
        System.out.println(address);
        String mobile = request.getParameter("mobile");
        String userMessage = request.getParameter("userMessage");

        Order order = new Order();
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        order.setOrderCode(orderCode);
        order.setAddress(address);
        order.setReceiver(receiver);
        order.setMobile(mobile);
        order.setUserMessage(userMessage);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setUid(user.getId());
        order.setStatus("waitPay");
        orderService.insert(order);

        float total = 0;
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }

        return "redirect:/alipay?oid=" + order.getId() + "&total=" + total;

    }

    @RequestMapping("/alipay")
    public String alipay(HttpServletRequest request, HttpServletResponse response, Integer page) {

        return "/alipay.html";
    }

    @RequestMapping("/payed")
    public String payed(HttpServletRequest request, HttpServletResponse response, Integer page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order order = orderService.findById(oid);
        order.setStatus("waitDelivery");
        order.setPayDate(new Date());
        orderService.update(order);
        request.setAttribute("o", order);
        return "/payed.html";
    }

    @RequestMapping("/bought")
    public String bought(HttpServletRequest request, HttpServletResponse response, Integer page) {
        User user = (User) request.getSession().getAttribute("user");
        List<Order> orders1 = orderService.findByUid(user.getId());
        List<Order> orders2 = new ArrayList<>();
        for(Order o : orders1){

            if(!o.getStatus().equals("delete")){
                orders2.add(o);
                System.out.println(o.getStatus());

            }

        }
        for (Order o : orders2) {
            List<OrderItem> ois = orderItemService.findByOid(o.getId());
            float total = 0;
            int totalNumber = 0;
            for (OrderItem oi : ois) {
                Product product = productService.findById(oi.getPid());
                List<ProductImage>pis = productImageService.findSingleByPid(product.getId());
                product.setFirstProductImage(pis.get(0));
                oi.setProduct(product);
                total += oi.getNumber() * oi.getProduct().getPromotePrice();
                totalNumber += oi.getNumber();
            }
            o.setTotal(total);
            o.setOrderItems(ois);
            o.setTotalNumber(totalNumber);
        }
        request.setAttribute("os", orders2);
        return "/bought.html";
    }

    @RequestMapping("/confirmPay")
    public String confirmPay(HttpServletRequest request, HttpServletResponse response, Integer page) {
        int oid = Integer.parseInt(request.getParameter("oid"));

        Order o = orderService.findById(oid);

        List<OrderItem> ois = orderItemService.findByOid(o.getId());
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            Product product = productService.findById(oi.getPid());
            List<ProductImage>pis = productImageService.findSingleByPid(product.getId());
            product.setFirstProductImage(pis.get(0));
            oi.setProduct(product);
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
        }

        o.setTotal(total);
        o.setOrderItems(ois);
        o.setTotalNumber(totalNumber);
        request.setAttribute("o", o);
        return "/confirmPay.html";
    }

    @RequestMapping("/orderConfirmed")
    public String orderConfirmed(HttpServletRequest request, HttpServletResponse response,Integer page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order o = orderService.findById(oid);
        o.setStatus("waitReview");
        o.setConfirmDate(new Date());
        orderService.update(o);
        return "/orderConfirmed.html";
    }

    @RequestMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(HttpServletRequest request, HttpServletResponse response,Integer page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order o = orderService.findById(oid);
        o.setStatus("delete");
        orderService.update(o);
        return "success";
    }

    @RequestMapping("/review")
    public String review(HttpServletRequest request, HttpServletResponse response, Integer page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        int pid = Integer.parseInt(request.getParameter("pid"));
        Order o = orderService.findById(oid);
        List<OrderItem> ois = orderItemService.findByOid(oid);
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            Product product = productService.findById(oi.getPid());
            List<ProductImage>pis = productImageService.findSingleByPid(product.getId());
            product.setFirstProductImage(pis.get(0));
            oi.setProduct(product);
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
        }
        o.setTotal(total);
        o.setOrderItems(ois);
        o.setTotalNumber(totalNumber);

        Product p = productService.findById(pid);
        List<Review> reviews = reviewService.findByPid(p.getId());
        List<OrderItem> orderItems = orderItemService.findByPid(p.getId());
        int sell = 0;
        for(OrderItem oi : orderItems){
            if(oi.getOid()>0)
                sell += oi.getNumber();
        }
        p.setSell(sell);
        p.setReviewCount(reviews.size());

        request.setAttribute("p", p);
        request.setAttribute("o", o);
        request.setAttribute("reviews", reviews);
        return "/review.html";
    }

    @RequestMapping("/doReview")
    public String doReview(HttpServletRequest request, HttpServletResponse response, Integer page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order o = orderService.findById(oid);
        o.setStatus("finish");
        orderService.update(o);
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p = productService.findById(pid);
        String content = request.getParameter("content");
        content = HtmlUtils.htmlEscape(content);

        User user = (User) request.getSession().getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setProduct(p);
        review.setCreateDate(new Date());
        review.setUser(user);
        review.setUid(user.getId());
        review.setPid(p.getId());
        reviewService.insert(review);

        //return "redirect:/review?oid=" + oid+"&pid="+pid + "&showonly=true";
        return "redirect:/bought";
    }


}