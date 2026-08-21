package br.com.coti.apiprodutos.controllers;

import br.com.coti.apiprodutos.entities.dtos.ProdutoAtualizarRequestDto;
import br.com.coti.apiprodutos.entities.dtos.ProdutoRequestDto;
import br.com.coti.apiprodutos.entities.mappers.ProdutoMapper;
import br.com.coti.apiprodutos.exceptions.CreateProdutoException;
import br.com.coti.apiprodutos.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutosController {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @PostMapping("criar")
    public ResponseEntity<?> inserir(@RequestBody ProdutoRequestDto dto) {
        try {
            var produto = produtoMapper.toProduto(dto);
            produtoRepository.inserir(produto);

            return ResponseEntity.status(HttpStatus.OK).body("Produto '" + produto.getNome() + "' criado com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CreateProdutoException("Erro na execução do método de criação de produto!", e.getCause());
        }
    }

    @PutMapping("alterar")
    public ResponseEntity<?> alterar(@RequestBody ProdutoAtualizarRequestDto dto) {
        try {
            var produto = produtoMapper.toProdutoAtualizar(dto);
            if (produtoRepository.atualizar(produto))
                return ResponseEntity.status(HttpStatus.OK).body("Produto '" + produto.getNome() + "' atualizado com sucesso!");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto '" + produto.getNome() + "' não encontrado!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CreateProdutoException("Erro na execução do método de criação de produto!", e.getCause());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        try {
            if (produtoRepository.excluir(id))
                return ResponseEntity.status(HttpStatus.OK).body("Produto com id: '" + id + "', foi inativado com sucesso!");
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto foi inativado!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("listar")
    public ResponseEntity<?> listar() {
        try {
            var listaProdutos = produtoRepository.listar();
            if (listaProdutos == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto cadastrado!");

            return ResponseEntity.status(HttpStatus.OK).body(produtoMapper.toDtoResponse(listaProdutos));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("buscarPorId/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            var produto = produtoMapper.toDtoResponse(produtoRepository.buscarPorId(id));
            if (produto == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto foi encontrado!");

            return ResponseEntity.status(HttpStatus.OK).body(produto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("buscarPorNome/{nome}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome) {
        try {
            var produtos = produtoMapper.toDtoResponse(produtoRepository.buscarPorNome(nome));
            if (produtos.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto foi encontrado!");

            return ResponseEntity.status(HttpStatus.OK).body(produtos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
