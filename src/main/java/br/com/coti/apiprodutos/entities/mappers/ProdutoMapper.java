package br.com.coti.apiprodutos.entities.mappers;

import br.com.coti.apiprodutos.entities.Produto;
import br.com.coti.apiprodutos.entities.dtos.ProdutoRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    ProdutoRequestDto toDto(Produto produto);
    Produto toProduto(ProdutoRequestDto produtoRequestDto);
}
