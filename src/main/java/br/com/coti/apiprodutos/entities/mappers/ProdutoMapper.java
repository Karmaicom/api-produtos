package br.com.coti.apiprodutos.entities.mappers;

import br.com.coti.apiprodutos.entities.Produto;
import br.com.coti.apiprodutos.entities.dtos.ProdutoAtualizarRequestDto;
import br.com.coti.apiprodutos.entities.dtos.ProdutoRequestDto;
import br.com.coti.apiprodutos.entities.dtos.ProdutoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    ProdutoRequestDto toDtoRequest(Produto produto);

    List<ProdutoResponseDto> toDtoResponse(List<Produto> produto);

    @Mapping(target = "total", expression = "java(produto.getPreco() * produto.getQuantidade())")
    ProdutoResponseDto toDtoResponse(Produto produto);

    Produto toProduto(ProdutoRequestDto produtoRequestDto);

    Produto toProdutoAtualizar(ProdutoAtualizarRequestDto produtoAtualizarRequestDto);
}
