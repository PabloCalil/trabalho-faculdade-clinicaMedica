package com.mycompany.clinicamedica.newpackage.view;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorAutenticacao {
    
    public static class Usuario {
        String login, senha, perfil, especialidade, crm;

        public Usuario(String login, String senha, String perfil) {
            this.login = login; 
            this.senha = senha; 
            this.perfil = perfil;
            this.especialidade = "N/A"; 
            this.crm = "N/A";
        }
    }

    // Lista de Usuários
    public static List<Usuario> bancoUsuarios = new ArrayList<>();
    
    // === NOVA LISTA: Memória global para as Especialidades ===
    public static List<String> listaEspecialidades = new ArrayList<>();

    static {
        // Usuários Padrão
        bancoUsuarios.add(new Usuario("admin", "123", "Administrador"));
        bancoUsuarios.add(new Usuario("secretaria", "123", "Secretária"));
        
        Usuario med = new Usuario("medico", "123", "Médico");
        med.especialidade = "Clínico Geral";
        med.crm = "12345-MG";
        bancoUsuarios.add(med);

        // Especialidades Padrão que já nascem com o sistema
        listaEspecialidades.add("Clínico Geral");
        listaEspecialidades.add("Cardiologia");
        listaEspecialidades.add("Pediatria");
        listaEspecialidades.add("Ortopedia");
    }
}