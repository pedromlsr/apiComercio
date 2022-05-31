package com.residencia.comercio.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "fornecedor")
@JsonIdentityInfo(scope = Fornecedor.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idFornecedor")
public class Fornecedor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_fornecedor")
	private Integer idFornecedor;

	@Column(name = "cnpj")
	@NotBlank(message = "O número do CNPJ não pode ficar em branco.")
	@CNPJ(message = "Confira o padrão do CNPJ inserido.")
	private String cnpj;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "razao_social")
	@NotBlank(message = "A razão social não pode ficar em branco.")
	private String razaoSocial;

	@Column(name = "uf")
	@Size(min = 2, max = 2, message = "A UF deve conter 2 caracteres.")
	private String uf;
	
	@Column(name = "telefone")
	@NotBlank(message = "O telefone não pode ficar em branco.")
	@Size(max = 13, message = "O telefone não pode conter mais do que 13 caracters.")
	@Pattern(regexp = "([(]?[0-9]{2}[)]?[0-9]{4}[-]?[0-9]{4})", message = "Confira o padrão do telefone.")
	private String telefone;

	@Column(name = "email")
	@NotBlank(message = "O email não pode ficar em branco.")
	@Email(message = "Confira o padrão do email inserido.")
	private String email;

	@Column(name = "nome_fantasia")
	@NotBlank(message = "O nome fantasia não pode ficar em branco.")
	private String nomeFantasia;

	@Column(name = "status_situacao")
	private String statusSituacao;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "logradouro")
	private String logradouro;

	@Column(name = "numero")
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "cep")
	@Pattern(regexp = "([0-9]{5}[-]?[0-9]{3})", message = "Confira o padrão do CEP inserido.")
	private String cep;

	@Column(name = "municipio")
	private String municipio;

	@Column(name = "data_abertura")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataAbertura;

	@OneToMany(mappedBy = "fornecedor")
	private List<Produto> produtoList;

	public Integer getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Integer idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getStatusSituacao() {
		return statusSituacao;
	}

	public void setStatusSituacao(String statusSituacao) {
		this.statusSituacao = statusSituacao;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public List<Produto> getProdutoList() {
		return produtoList;
	}

	public void setProdutoList(List<Produto> produtoList) {
		this.produtoList = produtoList;
	}

}