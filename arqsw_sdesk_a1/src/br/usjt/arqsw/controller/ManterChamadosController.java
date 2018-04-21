package br.usjt.arqsw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.ChamadoService;
import br.usjt.arqsw.service.FilaService;
/**
 * 
 * @author BrunaCamariniVieiraNunes-8162257981
 *
 */
@Transactional
@Controller
public class ManterChamadosController {
	private FilaService filaService;
	private ChamadoService chamadoService;

	@Autowired
	public ManterChamadosController(FilaService filaService, ChamadoService chamadoService) {
		this.filaService = filaService;
		this.chamadoService = chamadoService;
	}

	@RequestMapping("index")
	public String inicio() {
		return "index";
	}

	private List<Fila> listarFilas() throws IOException{
			return filaService.listarFilas();
	}
	
	@RequestMapping("/listar_filas")
	public String novoChamado(Model model){
		try{
			model.addAttribute("lista",listarFilas());
			return "NovoChamado";
		}catch(IOException e){
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/novo_chamado")
	public String criarChamado(@Valid Chamado chamado, BindingResult result, Model model) {
		if (result.hasFieldErrors("descricao")) {
			try {
				model.addAttribute("lista", listarFilas());
				return "NovoChamado";
			} catch (IOException e) {
				e.printStackTrace();
				return "Erro";
			}
		} else {
			try {
				int id = chamadoService.criarChamado(chamado);
				chamado.setNumero(id);
				return "NumeroChamado";
			} catch (IOException e) {
				e.printStackTrace();
				return "Erro";
			}
		}
	}
	
	@RequestMapping("/listar_filas_fechar")
	public String listarFilasFechar(Model model) {
		try {
			List<Fila> filas = filaService.listarFilas();
			model.addAttribute("filas", filas);
			return "ChamadoFechar";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/listar_chamados_abertos")
	public String listarChamadosAbertos(Fila fila, Model model) {
		try {
			fila = filaService.carregar(fila.getId());
			model.addAttribute("fila", fila);

			List<Chamado> chamados = chamadoService.listarChamadosAbertos(fila);
			model.addAttribute("chamados", chamados);
			return "ChamadoFecharSelecionar";

		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/fechar_chamados")
	public String fecharChamados(
			@RequestParam Map<String, String> allRequestParams) {
		try {
			Set<String> chaves = allRequestParams.keySet();
			Iterator<String> i = chaves.iterator();
			ArrayList<Integer> lista = new ArrayList<>();
			while (i.hasNext()) {
				String chave = i.next();
				String valor = allRequestParams.get(chave);
				if (valor.equals("on")) {
					lista.add(Integer.parseInt(chave));
				}
			}
			if (lista.size() > 0) {
				chamadoService.fecharChamados(lista);
			}
			return "ChamadoFechado";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/listar_filas_exibir")
	public String listarFilasExibir(Model model) {
		try {
			List<Fila> filas = filaService.listarFilas();
			model.addAttribute("filas", filas);
			return "ChamadoListar";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/listar_chamados_exibir")
	public String listarChamadosExibir(@Valid Fila fila, BindingResult result, Model model) {
		try {
			if (result.hasFieldErrors("id")) {
				model.addAttribute("filas", listarFilas());
				System.out.println("Deu erro " + result.toString());
				return "ChamadoListar";
				//return "redirect:listar_filas_exibir";
			}else{
			fila = filaService.carregar(fila.getId());
			model.addAttribute("fila", fila);
			
			List<Chamado> chamados = chamadoService.listarChamados(fila);
			model.addAttribute("chamados", chamados);
			return "ChamadoListarExibir";
			}

		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	

}
