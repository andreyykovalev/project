package com.epam.rd.controllers.admin;


import com.epam.rd.DataBaseUtility;
import com.epam.rd.model.ModelPackage;
import com.epam.rd.model.entity.EntityPackage;
import com.epam.rd.model.entity.EntityUser;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PackageManaging extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public void init() {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        HttpSession session = request.getSession();
        EntityUser admin = (EntityUser) session.getAttribute("admin");
        request.setAttribute("admin", admin);

        try {
            switch (action) {
                case "/admin/new":
                    showNewForm(request, response);
                    break;
                case "/admin/insert":
                    insertPackage(request, response);
                    break;
                case "/admin/delete":
                    deletePackage(request, response);
                    break;
                case "/admin/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/update":
                    updatePackage(request, response);
                    break;
                default:
                    listPackages(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listPackages(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        ModelPackage modelPackage = new ModelPackage(1);
        List<EntityPackage> listPackages = modelPackage.load();

        ModelPackage modelPackageRus = new ModelPackage(2);
        List<EntityPackage> listPackagesRus = modelPackageRus.load();

        request.setAttribute("listPackages", listPackages);
        request.setAttribute("listPackagesRus", listPackagesRus);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Tariffs.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("AddTariffs.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        ModelPackage modelPackage = new ModelPackage(1);
        long id = Integer.parseInt(request.getParameter("id"));
        EntityPackage existingPackage = modelPackage.loadById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("AddTariffs.jsp");
        request.setAttribute("product", existingPackage);
        dispatcher.forward(request, response);

    }

    private void insertPackage(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        BasicDataSource dataSource = DataBaseUtility.getDataSource();
        ModelPackage modelPackage = new ModelPackage(dataSource.getConnection(), 1);

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        Integer type = Integer.parseInt(request.getParameter("type"));
        Integer languageId = Integer.parseInt(request.getParameter("langId"));
        double price = Double.parseDouble(request.getParameter("price"));

        EntityPackage newPackage = EntityPackage.builder()
                .name(name)
                .description(description)
                .image(image)
                .type(type)
                .languageId(languageId)
                .price(price).build();

        modelPackage.addProduct(newPackage);

        response.sendRedirect("list");
    }

    private void updatePackage(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        ModelPackage modelPackage = new ModelPackage(1);

        long id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        Integer type = Integer.parseInt(request.getParameter("type"));
        Integer languageId = Integer.parseInt(request.getParameter("langId"));
        double price = Double.parseDouble(request.getParameter("price"));

        EntityPackage newPackege = EntityPackage.builder()
                .id(id)
                .name(name)
                .description(description)
                .image(image)
                .type(type)
                .languageId(languageId)
                .price(price).build();

        modelPackage.update(newPackege);
        response.sendRedirect("list");
    }

    private void deletePackage(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        ModelPackage modelPackage = new ModelPackage(1);
        long id = Integer.parseInt(request.getParameter("id"));

        EntityPackage pack = EntityPackage.builder().id(id).build();
        modelPackage.delete(pack);
        response.sendRedirect("list");
    }
}