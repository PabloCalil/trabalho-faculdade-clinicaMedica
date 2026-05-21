# 🏥 Sistema de Gestão para Clínica Médica

Este é um projeto acadêmico de um sistema desktop voltado para o gerenciamento de uma clínica médica, desenvolvido utilizando a linguagem **Java** e a IDE **NetBeans**. O sistema visa automatizar processos primordiais como agendamentos, cadastros e controle de acessos.

---

## 🚀 Funcionalidades Planejadas (Escopo)

O projeto está dividido nos seguintes módulos principais:

- [ ] **Módulo de Autenticação (Login):** Níveis de acesso restritos para Administradores, Médicos e Recepcionistas.
- [ ] **Módulo de Cadastros:** Registro completo de Pacientes (dados pessoais e convênio) e Médicos (CRM e Especialidade).
- [ ] **Módulo de Agendamentos:** Gerenciamento de horários, marcação e cancelamento de consultas.
- [ ] **Módulo de Prontuário:** Histórico de consultas, anamnese e prescrição de medicamentos.

---

## 📁 Estrutura de Pastas do Projeto

Para manter o projeto organizado em equipe, utilizaremos o padrão MVC (Model-View-Controller). A estrutura de pacotes dentro de `Source Packages` (`src/main/java`) deve seguir este modelo:

* `com.mycompany.clinicamedica` (Pacote Principal)
    * `model/`: Classes que representam as entidades do sistema (ex: `Paciente.java`, `Medico.java`) e classes de conexão com o Banco de Dados (DAO).
    * `view/`: Telas e interfaces gráficas do sistema (arquivos JFrame/Java Swing).
    * `controller/`: Classes responsáveis pelas regras de negócio e por ligar a Interface (View) aos Dados (Model).

---

## 🌿 Estratégia de Branchs (Trabalho em Equipe)

Como estamos trabalhando em 4 desenvolvedores, adotamos as seguintes regras de versionamento:

* `main` (ou `master`): Código 100% estável e testado, pronto para a entrega final.
* `feature/`: Branchs criadas individualmente para desenvolver uma tela ou função específica.
    * *Exemplos:* `feature/tela-login`, `feature/banco-dados`, `feature/cadastro-pacientes`.

⚠️ **Importante:** Nunca façam alterações diretas na branch principal. Sempre criem uma `feature/` e utilizem Pull Requests no GitHub para integrar o código.

---

## 👥 Desenvolvedores

* **⚡ Pablo Calil Lemes de Sousa**
* **👥 Diego Alves**
* **👥 Alexandre Freitas**
* **👥 João Vitor de Ávila**

---

## 🛠️ Tecnologias e Ferramentas

* **Linguagem:** Java
* **IDE:** Apache NetBeans (v30)
* **Interface Gráfica:** Java Swing / GUI Builder
* **Banco de Dados:** MySQL

