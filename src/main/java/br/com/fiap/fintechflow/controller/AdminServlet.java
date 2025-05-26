package br.com.fiap.fintechflow.controller;

import br.com.fiap.fintechflow.exception.BusinessException;
import br.com.fiap.fintechflow.model.Usuario;
import br.com.fiap.fintechflow.service.UsuarioService;
import br.com.fiap.fintechflow.service.impl.UsuarioServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/clientes")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.usuarioService = new UsuarioServiceImpl();
        } catch (SQLException e) {
            throw new ServletException("Erro ao inicializar UsuarioService: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Usuario> clientes = usuarioService.listarTodos();
            request.setAttribute("clientes", clientes);
            request.getRequestDispatcher("listarClientes.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Erro ao listar clientes: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/listarClientes.jsp").forward(request, response);
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/listarClientes.jsp").forward(request, response);
        }
    }
}