package br.univille.dacs2022.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProcedimentoDTO {
    private long id;
    @NotBlank(message = "O campo Descrição não pode ser deixado em branco.")
    @NotNull
    private String descricao;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((ProcedimentoDTO) obj).getId();
    }
    
}
