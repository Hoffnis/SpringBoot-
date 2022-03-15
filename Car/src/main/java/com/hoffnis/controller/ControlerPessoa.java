package com.hoffnis.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hoffnis.model.Pessoa;
import com.hoffnis.model.Proprietario;
import com.hoffnis.repository.PessoaRepository;
import com.hoffnis.repository.ProprietarioRepository;

@Controller
public class ControlerPessoa {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ProprietarioRepository proprietarioRepository;
	
	@RequestMapping(method=RequestMethod.GET, value="/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView mode = new ModelAndView("cadastro/cadastropessoa");
		mode.addObject("pessoaobj", new Pessoa());
		return mode;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			ModelAndView mode = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			mode.addObject("pessoas", pessoasIt);
			mode.addObject("pessoaobj", pessoa);
			
			List<String> msg =new ArrayList<String>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			mode.addObject("msg", msg);
			return mode;
		}
		
		pessoaRepository.save(pessoa);
		ModelAndView model = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		model.addObject("pessoas", pessoasIt);
		model.addObject("pessoaobj", new Pessoa());
		return model;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/listapessoas")
	public ModelAndView pessoas() {
		ModelAndView model = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		model.addObject("pessoas", pessoasIt);
		model.addObject("pessoaobj", new Pessoa());
		return model;
	}
	
	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView mode = new ModelAndView("cadastro/cadastropessoa");
		mode.addObject("pessoaobj", pessoa.get());
		return mode;
	}
	
	@GetMapping("/removerpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {
	
		pessoaRepository.deleteById(idpessoa);
		
		ModelAndView mode = new ModelAndView("cadastro/cadastropessoa");
		mode.addObject("pessoaobj", pessoaRepository.findAll());
		mode.addObject("pessoaobj", new Pessoa());
		return mode;
	}
	
	
	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView mode = new ModelAndView("cadastro/cadastropessoa");
		mode.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
		mode.addObject("pessoaobj", new Pessoa());
		return mode;
	
	}
	
	@GetMapping("/proprietario/{idpessoa}")
	public ModelAndView proprietario(@PathVariable("idpessoa") Long idpessoa) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView mode = new ModelAndView("cadastro/proprietario");
		mode.addObject("pessoaobj", pessoa.get());
		mode.addObject("proprietario", proprietarioRepository.getProprietario(idpessoa));
		return mode;
	}
	
	@PostMapping("**/adicionardados/{pessoaid}")
	public ModelAndView adicionarDados(Proprietario proprietario, @PathVariable("pessoaid") Long pessoaid) {
		
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		proprietario.setPessoa(pessoa);
		
		proprietarioRepository.save(proprietario);
		ModelAndView mode = new ModelAndView("cadastro/proprietario");
		mode.addObject("pessoaobj", pessoa);
		mode.addObject("proprietario", proprietarioRepository.getProprietario(pessoaid));
		return mode;
	}
	
	@GetMapping("/removerproprietario/{idproprietario}")
	public ModelAndView deletar(@PathVariable("idproprietario") Long idproprietario) {
		
		Pessoa pessoa = proprietarioRepository.findById(idproprietario).get().getPessoa();
		proprietarioRepository.deleteById(idproprietario);
		
		ModelAndView mode = new ModelAndView("cadastro/proprietario");
		mode.addObject("pessoaobj", pessoa);
		mode.addObject("proprietario", proprietarioRepository.getProprietario(pessoa.getId()));
		return mode;
	}

}
