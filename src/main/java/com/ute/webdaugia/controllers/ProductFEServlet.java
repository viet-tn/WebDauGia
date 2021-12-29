package com.ute.webdaugia.controllers;

import com.ute.webdaugia.beans.*;
import com.ute.webdaugia.models.AdminUserModel;
import com.ute.webdaugia.models.ProductModel;
import com.ute.webdaugia.models.OrderModel;
import com.ute.webdaugia.utils.ServletUtils;
import org.sql2o.converters.Convert;
import java.time.Duration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.ute.webdaugia.models.ProductModel.*;


@WebServlet(name = "ProductFEServlet", value = "/Product/*")
public class ProductFEServlet extends HttpServlet {

    final int itemsPerPage = 3;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        switch (path) {
            case "/ByCat":
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("authUser");
                List<Wishlist> wlist1 = ProductModel.findAllWList(user.getIdUser());

                int catId = Integer.parseInt(request.getParameter("id"));
                List<Product> products = ProductModel.findByCatId(catId);

                List<User> listuser1 = ProductModel.danhsachtenUser();
                request.setAttribute("listuser1", listuser1);
                List<SoLuotDauGia> listragia = ProductModel.SoluotraGiaByCat(catId);
                request.setAttribute("listragia", listragia);

                if (products == null) {
                    request.setAttribute("products", products);
                    request.setAttribute("pageNo", 1);
                    request.setAttribute("pages", 1);
                    ServletUtils.forward("/views/vwProduct/ByCat.jsp", request, response);
                    return;
                }

                int pageNo = 1;
                try {
                    pageNo = Integer.parseInt(request.getParameter("p"));
                } catch (NumberFormatException e) {
                }

                int pages = products.size() / itemsPerPage;
                if (products.size() % itemsPerPage != 0) {
                    ++pages;
                }

                if (pageNo > pages || pageNo <= 0) {
                    ServletUtils.forward("/views/404.jsp", request, response);
                    return;
                }

                if (pages == 1) {
                    request.setAttribute("pageNo", pageNo);
                    request.setAttribute("products", products);
                    request.setAttribute("pages", pages);
                    request.setAttribute("wlists", wlist1);
                    ServletUtils.forward("/views/vwProduct/ByCat.jsp", request, response);
                    return;
                }

                List<Product> subList = null;
                if (pageNo == pages) {
                    subList = products.subList(itemsPerPage * (pageNo - 1), products.size());
                } else {
                    subList = products.subList(itemsPerPage * (pageNo - 1), itemsPerPage * pageNo);
                }
                request.setAttribute("pageNo", pageNo);
                request.setAttribute("pages", pages);
                request.setAttribute("products", subList);
                request.setAttribute("wlists", wlist1);
                ServletUtils.forward("/views/vwProduct/ByCat.jsp", request, response);
                break;

            case "/Detail":
                int proId = Integer.parseInt(request.getParameter("id"));
                Product product = ProductModel.findById(proId);
                if (product == null) {
                    ServletUtils.redirect("/Home", request, response);
                } else {
                    request.setAttribute("product", product);
                    int userId2 =0;
            HttpSession session2 = request.getSession();
            User user2= (User) session2.getAttribute("authUser");
                    request.setAttribute("user",user2.getIdUser());
                    List<Wishlist> wlist2=ProductModel.findAllWList(user2.getIdUser());
                    request.setAttribute("wlists",wlist2);
                    User mark = ProductModel.diemdanhgia(user2.getIdUser());
                    request.setAttribute("mark",mark);
                    List<Orders> lichsu = ProductModel.LichSuDauGia(proId);
                    request.setAttribute("lichsu",lichsu);
                    List<User> listuser = ProductModel.danhsachtenUser();
                    request.setAttribute("listuser",listuser);
                    Integer soluotragia = ProductModel.SoluotraGia(proId);
                    request.setAttribute("soluotragia",soluotragia);
                    ServletUtils.forward("/views/vwProduct/Detail.jsp", request, response);
                }
                break;

            case "/ByParentCatID":
                int PaCaID = Integer.parseInt(request.getParameter("id"));
                List<Product> productsByParentID = ProductModel.findbyparentID(PaCaID);
                List<User> list2 = AdminUserModel.findAllUser_verpa();
                List<Product> list3 = ProductModel.find_all_product_per1();

                if (productsByParentID == null) {
                    request.setAttribute("products", productsByParentID);
                    request.setAttribute("pageNo", 1);
                    request.setAttribute("pages", 1);
                    ServletUtils.forward("/views/vwProduct/ByParentID.jsp", request, response);
                    return;
                }

                int proByParentIdPageNo = 1;
                try {
                    proByParentIdPageNo = Integer.parseInt(request.getParameter("p"));
                } catch (NumberFormatException e) {
                }

                int proByParentIdPages = productsByParentID.size() / itemsPerPage;
                if (productsByParentID.size() % itemsPerPage != 0) {
                    ++proByParentIdPages;
                }

                if (proByParentIdPageNo > proByParentIdPages || proByParentIdPageNo <= 0) {
                    ServletUtils.forward("/views/404.jsp", request, response);
                    return;
                }

                if (proByParentIdPages == 1) {
                    request.setAttribute("pageNo", proByParentIdPageNo);
                    request.setAttribute("products", productsByParentID);
                    request.setAttribute("pages", proByParentIdPages);
                    ServletUtils.forward("/views/vwProduct/ByParentID.jsp", request, response);
                    return;
                }

                List<Product> proByParentIDSubList = null;
                if (proByParentIdPageNo == proByParentIdPages) {
                    proByParentIDSubList = productsByParentID.subList(itemsPerPage * (proByParentIdPageNo - 1), productsByParentID.size());
                } else {
                    proByParentIDSubList = productsByParentID.subList(itemsPerPage * (proByParentIdPageNo - 1), itemsPerPage * proByParentIdPageNo);
                }
                request.setAttribute("pageNo", proByParentIdPageNo);
                request.setAttribute("pages", proByParentIdPages);

                //                Duration a=Orders.main()
                request.setAttribute("products_PaCaID", proByParentIDSubList);
                request.setAttribute("list_user", list2);
                request.setAttribute("list_date_update", list3);
                ServletUtils.forward("/views/vwProduct/ByParentID.jsp", request, response);
                break;

            case "/addwatlist":
                int catId1 = 0;
                HttpSession session3 = request.getSession();
                User user3 = (User) session3.getAttribute("authUser");
                int watId = 0;
                watId = Integer.parseInt(request.getParameter("id_product"));
                addWatchList(user3.getIdUser(), watId);
                catId1 = findidCatByidproduct(watId);
                String urlproduct = "/Product/ByCat?id=" + Integer.toString(catId1);
                ServletUtils.redirect(urlproduct, request, response);
                break;
            case "/addwatlistDetail":
                HttpSession session4 = request.getSession();
                User user4 = (User) session4.getAttribute("authUser");
                int watId4 = 0;
                watId4 = Integer.parseInt(request.getParameter("id_product"));
                addWatchList(user4.getIdUser(), watId4);
                String urlproduct4 = "/Product/Detail?id=" + Integer.toString(watId4);
                ServletUtils.redirect(urlproduct4, request, response);
                break;
            case "/delwatlist":
                int catId2 = 0;
                HttpSession session6 = request.getSession();
                User user6 = (User) session6.getAttribute("authUser");
                int watId2 = 0;
                watId2 = Integer.parseInt(request.getParameter("id_product"));
                delWatchList(user6.getIdUser(), watId2);
                catId2 = findidCatByidproduct(watId2);
                String urlproduct2 = "/Product/ByCat?id=" + Integer.toString(catId2);
                ServletUtils.redirect(urlproduct2, request, response);
                break;
            case "/delwatlistDetail":
                HttpSession session10 = request.getSession();
                User user10 = (User) session10.getAttribute("authUser");
                int watId10 = 0;
                watId10 = Integer.parseInt(request.getParameter("id_product"));
                delWatchList(user10.getIdUser(), watId10);
                String urlproduct10 = "/Product/Detail?id=" + Integer.toString(watId10);
                ServletUtils.redirect(urlproduct10, request, response);
                break;
            case "/delwatlistinWatList":
                HttpSession session5 = request.getSession();
                User user5 = (User) session5.getAttribute("authUser");
                int watId3 = 0;
                watId3 = Integer.parseInt(request.getParameter("id_product"));
                delWatchList(user5.getIdUser(), watId3);
                List<Product> wlist2 = ProductModel.findByIdWatList(user5.getIdUser());
                request.setAttribute("products", wlist2);
                ServletUtils.forward("/views/vwProduct/WatList.jsp", request, response);
                break;
            case "/WatList":
                HttpSession session7 = request.getSession();
                User user7 = (User) session7.getAttribute("authUser");
                List<Product> wlist = ProductModel.findByIdWatList(user7.getIdUser());
                request.setAttribute("products", wlist);
                ServletUtils.forward("/views/vwProduct/WatList.jsp", request, response);
                break;
            case "/Search":
                String txtSearch = request.getParameter("txtSearch");
                String show = request.getParameter("show");
                String sort = request.getParameter("sort");

                int searchPageNo = 1;
                try {
                    searchPageNo = Integer.parseInt(request.getParameter("p"));
                } catch (NumberFormatException e) {
                }
                pagingSearchResult(request, response, txtSearch, searchPageNo, show, sort);
                break;

            default:
                ServletUtils.forward("/views/404.jsp", request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        switch (path) {
            case "/Search":
                String txtSearch = request.getParameter("search-box");
                String show = request.getParameter("show");
                String sort = request.getParameter("sort");
                int pageNo = 1;
                try {
                    pageNo = Integer.parseInt(request.getParameter("p"));
                } catch (NumberFormatException e) {
                }
                pagingSearchResult(request, response, txtSearch, pageNo, show, sort);
                break;

            case "/addwatlist":
                int userId = 1;
                int watId = 5;
                addWatchList(userId, watId);
                ServletUtils.forward("/views/vwProduct/ByCat.jsp", request, response);
                break;
            case "/WatLDetail":
                int userId1 = 1;
                int watId1 = Integer.parseInt(request.getParameter("id"));
                addWatchList(userId1, watId1);
                ServletUtils.forward("/views/vwProduct/Detail.jsp", request, response);
                break;
            case "/Detail":
                BiderRaGia(request, response);
                // ServletUtils.forward("/views/vwProduct/ByCat.jsp", request, response);
                break;
            default:
                ServletUtils.forward("/views/404.jsp", request, response);
                break;
        }
    }

    private void BiderRaGia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("authUser");

        int id_Product = Integer.parseInt(request.getParameter("id"));
        int id_User = 2;
        int Price = Integer.parseInt(request.getParameter("Price"));
        int rule = 0;
        Orders c = new Orders(id_Product, user.getIdUser(), Price);
        ProductModel.BiderRaGia(c);
        System.out.println("abc");
        String urlproduct = "/Product/Detail?id=" + Integer.toString(id_Product);
        ServletUtils.redirect(urlproduct, request, response);
    }

    private void pagingSearchResult(HttpServletRequest request, HttpServletResponse response, String txtSearch, int pageNo, String show, String sort) throws ServletException, IOException {
        List<Product> products = ProductModel.findBySearch(txtSearch, show);
        request.setAttribute("searchShow", show);

        if (products != null) {
            if (Objects.equals(sort, "priceasc")) {
                products.sort(Comparator.comparing(Product::getCurrent_Price));
            }

            if (Objects.equals(sort, "pricedes")) {
                products.sort(Comparator.comparing(Product::getCurrent_Price).reversed());
            }

            if (Objects.equals(sort, "time")) {
                products.sort(Comparator.comparing(Product::getTime_to_close));
            }
        }
        request.setAttribute("searchSort", sort);

        if (products == null) {
            request.setAttribute("products", products);
            request.setAttribute("txtSearch", txtSearch);
            request.setAttribute("pageNo", 1);
            request.setAttribute("pages", 1);
            ServletUtils.forward("/views/vwProduct/Search.jsp", request, response);
            return;
        }
        int pages = products.size() / itemsPerPage;
        if (products.size() % itemsPerPage != 0 || pages == 0) {
            ++pages;
        }

        if (pageNo > pages || pageNo <= 0) {
            ServletUtils.forward("/views/404.jsp", request, response);
            return;
        }

        if (pages == 1) {
            request.setAttribute("products", products);
            request.setAttribute("txtSearch", txtSearch);
            request.setAttribute("pageNo", 1);
            request.setAttribute("pages", 1);
            ServletUtils.forward("/views/vwProduct/Search.jsp", request, response);
            return;
        }

        List<Product> subList = null;
        if (pageNo == pages) {
            subList = products.subList(itemsPerPage * (pageNo - 1), products.size());
        } else {
            subList = products.subList(itemsPerPage * (pageNo - 1), itemsPerPage * pageNo);
        }
        request.setAttribute("pageNo", pageNo);
        request.setAttribute("products", subList);
        request.setAttribute("txtSearch", txtSearch);
        request.setAttribute("pages", pages);
        ServletUtils.forward("/views/vwProduct/Search.jsp", request, response);
    }
}

