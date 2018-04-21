package br.usjt.arqsw.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.usjt.arqsw.entity.Usuario;
import br.usjt.arqsw.service.UsuarioService;
/**
 * 
 * @author BrunaCamariniVieiraNunes-8162257981
 *
 */
@Controller
public class LoginController {
	private UsuarioService usuarioService;
	
	@Autowired
	public LoginController(UsuarioService usuarioService){
		this.usuarioService = usuarioService;
	}
	
	@RequestMapping("loginForm")
	public String loginForm(){
		return "Login";
	}
	
	@RequestMapping("fazer_login")
	public String fazerLogin(Usuario usuario, HttpSession session){
		try{
			if(usuarioService.validarUsuario(usuario)){
				session.setAttribute(Usuario.LOGADO, usuario);
				return "index";
			}
		}catch(IOException e){
			e.printStackTrace();
			return "Erro";
		}
		
		return "redirect:loginForm";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session){
		session.setAttribute(Usuario.LOGADO, null);
		return "index";
	}

}
