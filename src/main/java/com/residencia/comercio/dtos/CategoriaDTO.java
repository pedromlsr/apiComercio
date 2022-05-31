package com.residencia.comercio.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoriaDTO {
	private Integer idCategoria;
	
	@NotBlank(message = "O nome da categoria não pode ficar em branco.")
	@Size(max = 50, message = "O nome da categoria não pode conter mais do que 50 caracteres.")
	private String nomeCategoria;

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	@Override
	public String toString() {
		return "CategoriaDTO [idCategoria=" + idCategoria + ", nomeCategoria=" + nomeCategoria + "]";
	}

}
