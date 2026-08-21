package br.com.coti.apiprodutos.entities.dtos;

import java.time.LocalDateTime;

public record ProdutoResponseDto(
        Integer id,
        String nome,
        String descricao,
        Double preco,
        Integer quantidade,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao,
        Double total
) {
}
