package com.residencia.comercio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.comercio.dtos.ProdutoDTO;
import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.repositories.CategoriaRepository;
import com.residencia.comercio.repositories.FornecedorRepository;
import com.residencia.comercio.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	FornecedorRepository fornecedorRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

	public List<Produto> findAllProduto() {
		return produtoRepository.findAll();
	}

	public Produto findProdutoById(Integer id) {
		return produtoRepository.existsById(id) ? produtoRepository.findById(id).get() : null;
	}

	public ProdutoDTO findProdutoDTOById(Integer id) {
		return produtoRepository.existsById(id) ? convertEntityToDTO(produtoRepository.findById(id).get()) : null;
	}

	public Produto saveProduto(Produto produto) {
		return produtoRepository.save(produto);
	}

	public ProdutoDTO saveProdutoDTO(ProdutoDTO produtoDTO) {
		return convertEntityToDTO(produtoRepository.save(convertDTOToEntity(produtoDTO)));
	}

	public Produto updateProduto(Produto produto) {
		return produtoRepository.save(produto);
	}

//	public Produto updateProdutoById(Produto produto, Integer id) {
//		Produto produtoBD = produtoRepository.findById(id).isPresent()
//				? produtoRepository.findById(id).get()
//				: null;
//		
//		Produto produtoAtualizado = null;
//		if(null != produtoBD) {
//			produtoBD.setNomeProduto(produto.getNomeProduto());
//			produtoBD.setSku(produto.getSku());
//			produtoBD.setFornecedor(produto.getFornecedor());
//			produtoBD.setCategoria(produto.getCategoria());
//			
//			produtoAtualizado = produtoRepository.save(produtoBD);
//			//...
//		}
//	}

	public void deleteProduto(Produto produto) {
		produtoRepository.delete(produto);
	}

	public void deleteProdutoById(Integer id) {
		produtoRepository.deleteById(id);
	}

	private Produto convertDTOToEntity(ProdutoDTO produtoDTO) {
		Produto produto = new Produto();

		produto.setIdProduto(produtoDTO.getIdProduto());
		produto.setSku(produtoDTO.getSku());
		produto.setNomeProduto(produtoDTO.getNomeProduto());

		if (produtoDTO.getIdFornecedor() != null) {
			produto.setFornecedor(fornecedorRepository.findById(produtoDTO.getIdFornecedor()).get());
		} else {
			produto.setFornecedor(null);
		}

		if (produtoDTO.getIdCategoria() != null) {
			produto.setCategoria(categoriaRepository.findById(produtoDTO.getIdCategoria()).get());
		} else {
			produto.setCategoria(null);
		}

		return produto;
	}

	private ProdutoDTO convertEntityToDTO(Produto produto) {
		ProdutoDTO produtoDTO = new ProdutoDTO();

		produtoDTO.setIdProduto(produto.getIdProduto());
		produtoDTO.setSku(produto.getSku());
		produtoDTO.setNomeProduto(produto.getNomeProduto());

		if (produto.getFornecedor() != null) {
			produtoDTO.setIdFornecedor(produto.getFornecedor().getIdFornecedor());
			produtoDTO.setNomeFornecedor(produto.getFornecedor().getNomeFantasia());
		}

		if (produto.getCategoria() != null) {
			produtoDTO.setIdCategoria(produto.getCategoria().getIdCategoria());
			produtoDTO.setNomeCategoria(produto.getCategoria().getNomeCategoria());
		}

		return produtoDTO;
	}

}
