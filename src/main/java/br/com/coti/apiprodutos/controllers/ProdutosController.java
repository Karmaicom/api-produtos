package br.com.coti.apiprodutos.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutosController {

    @PostMapping("criar")
    public String criar() {
        return "Produto criado com sucesso";
    }

    @PutMapping("alterar")
    public String alterar() {
        return "Produto alterado com sucesso";
    }

    @DeleteMapping
    public String excluir() {
        return "Produto excluído com sucesso";
    }

    @GetMapping("listar")
    public String listar() {
        return "Produto consultado com sucesso";
    }

}
