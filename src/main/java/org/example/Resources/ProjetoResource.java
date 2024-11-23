package org.example.Resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Exception.CategoriaAtividadeException;
import org.example.Exception.CategoriaAtividadeInvalidaException;
import org.example.Exception.DatabaseException;
import org.example.Exception.RelatorioNotFoundException;
import org.example.Model.*;
import org.example.Service.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Path("/caminho")
public class ProjetoResource {

    private final EmissaoService emissaoService = new EmissaoService();
    private final RelatorioEmissaoService relatorioEmissaoService = new RelatorioEmissaoService();
    private final RelatorioService relatorioService = new RelatorioService();
    private final UsuarioService usuarioService = new UsuarioService();

    // Constantes de Mensagens
    private static final String EMISSAO_CRIADA_SUCESSO = "{\"message\":\"Emissão criada com sucesso.\"}";
    private static final String EMISSAO_ERRO = "{\"message\":\"Erro ao criar a emissão.\"}";
    private static final String CATEGORIA_ERRO = "{\"message\":\"Categoria com ID %d não encontrada.\"}";
    private static final String CATEGORIA_CRIADA_SUCESSO = "{\"message\":\"Categoria criada com sucesso.\"}";
    private static final String CATEGORIA_ATUALIZADA_SUCESSO = "{\"message\":\"Categoria atualizada com sucesso.\"}";
    private static final String CATEGORIA_EXCLUIDA_SUCESSO = "{\"message\":\"Categoria excluída com sucesso.\"}";
    private static final String CATEGORIA_NAO_ENCONTRADA = "{\"message\":\"Nenhuma categoria encontrada.\"}";
    private static final String EMISSOES_NAO_ENCONTRADAS = "{\"message\":\"Nenhuma emissão encontrada.\"}";

    // Métodos de Emissão
    @GET
    @Path("/emissoes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEmissoesComCategorias() {
        List<Emissao> emissoes = emissaoService.listarEmissoesComCategorias();
        if (emissoes == null || emissoes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(EMISSOES_NAO_ENCONTRADAS)
                    .build();
        }
        return Response.ok(emissoes).build();
    }

    @POST
    @Path("/emissao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarEmissao(Emissao emissao) {
        try {
            emissaoService.criarEmissao(emissao); // Verifique se a data está sendo passada corretamente aqui
            return Response.status(Response.Status.CREATED)
                    .entity(Collections.singletonMap("mensagem", "Emissão criada com sucesso!"))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar emissão: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarEmissao(@PathParam("id") int id, Emissao emissao) {
        try {
            if (emissao.getDataEmissao() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("A data de emissão não pode ser nula.")
                        .build();
            }

            if (!emissaoService.idExiste(id)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"ID não encontrado.\"}")
                        .build();
            }

            boolean sucesso = emissaoService.atualizarEmissao(id, emissao);
            if (sucesso) {
                return Response.status(Response.Status.OK)
                        .entity("{\"message\": \"Emissão atualizada com sucesso.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"Erro ao atualizar a emissão.\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocorreu um erro no servidor: " + e.getMessage() + "\"}")
                    .build();
        }
    }




    @DELETE
    @Path("/exclemissao/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirEmissao(@PathParam("id") Integer id) throws DatabaseException {
        boolean sucesso = emissaoService.excluirEmissao(id);
        if (sucesso) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Emissão excluída com sucesso.\"}")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Erro ao excluir a emissão.\"}")
                    .build();
        }
    }



    // Métodos de CRUD para Categorias
    private final CategoriaAtividadeService categoriaAtividadeService = new CategoriaAtividadeService();

    // Método para listar todas as categorias
    @GET
    @Path("/categorias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarCategorias() throws SQLException {
        List<CategoriaAtividade> categorias = categoriaAtividadeService.listarCategorias();
        if (categorias.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Nenhuma categoria encontrada.\"}")
                    .build();
        }
        return Response.ok(categorias).build();
    }


    // Método para criar uma nova categoria
    @POST
    @Path("/categorias/criacategoria")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarCategoria(CategoriaAtividade categoria) {
        boolean sucesso = categoriaAtividadeService.criarCategoria(categoria);
        if (sucesso) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\":\"Categoria criada com sucesso.\"}")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Erro ao criar a categoria.\"}")
                    .build();
        }
    }

        // Método para atualizar uma categoria existente
        @PUT
        @Path("/categorias/attcategoria")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response atualizarCategoria(CategoriaAtividade categoria) throws SQLException, CategoriaAtividadeException, DatabaseException {
            boolean sucesso = categoriaAtividadeService.atualizarCategoria(categoria);
            if (sucesso) {
                return Response.status(Response.Status.OK)
                        .entity("{\"message\":\"Categoria atualizada com sucesso.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\":\"Erro ao atualizar a categoria.\"}")
                        .build();
            }
        }


    // Método para excluir uma categoria
    @DELETE
    @Path("/categorias/exclcategoria/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirCategoria(@PathParam("id") Integer id) {
        try {
            boolean sucesso = categoriaAtividadeService.excluirCategoria(id);

            if (sucesso) {
                return Response.status(Response.Status.OK)
                        .entity("{\"message\":\"Categoria excluída com sucesso.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND) // Usar 404 quando não encontrar a categoria
                        .entity("{\"message\":\"Categoria não encontrada para exclusão.\"}")
                        .build();
            }
        } catch (DatabaseException e) {
            // Aqui, podemos fornecer detalhes da exceção para facilitar a depuração
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // 500 para erros internos
                    .entity("{\"message\":\"Erro ao excluir a categoria: " + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Erro inesperado: " + e.getMessage() + "\"}")
                    .build();
        }
    }



    // Métodos de CRUD para MetaReducao
    private final MetaReducaoService metaReducaoService = new MetaReducaoService();
    @GET
    @Path("/metareducoes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMetaReducao() throws DatabaseException {
        List<MetaReducao> metaReducaoList = metaReducaoService.listarMetaReducao();
        return Response.ok(metaReducaoList).build();
    }

    @POST
    @Path("/criametared")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarMeta(MetaReducao metaReducao) throws DatabaseException {
        boolean sucesso = metaReducaoService.salvarMetaReducao(metaReducao);
        if (sucesso) {
            return Response.status(Response.Status.CREATED).entity("Meta de redução criado com sucesso").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar o meta de redução").build();
        }
    }

    @PUT
    @Path("/{idMeta}/attmeta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarMeta(@PathParam("idMeta") int idMeta, MetaReducao meta) {
        try {
            // Verifique se os valores estão corretos aqui
            if (meta.getNome() == null || meta.getDescricao() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Nome ou descrição não podem ser nulos.")
                        .build();
            }

            // Configura o ID na meta recebida para garantir consistência
            meta.setId(idMeta);

            // Invoca o serviço para atualizar os campos fornecidos
            MetaReducaoService service = new MetaReducaoService();
            service.atualizarMeta(meta);

            // Retorna resposta de sucesso
            return Response.status(Response.Status.OK).entity("Meta atualizada com sucesso.").build();
        } catch (Exception e) {
            // Retorna erro em caso de falha
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }



    @GET
    @Path("/{idMeta}/verificarmeta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarMeta(@PathParam("idMeta") int idMeta) {
        try {
            boolean atingida = metaReducaoService.verificarMetaAtingida(idMeta);
            if (atingida) {
                return Response.status(Response.Status.OK).entity("Meta Atingida").build();
            } else {
                return Response.status(Response.Status.OK).entity("Meta não atingida ainda").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }



    // Métodos de CRUD para Recompensa
    private final RecompensaService recompensaService = new RecompensaService();

    @GET
    @Path("/recompensas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRecompensas() throws DatabaseException {
        List<Recompensa> recompensas = recompensaService.listarRecompensas();
        if (recompensas.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(recompensas).build();
    }

    // Método para criar uma nova recompensa
    @POST
    @Path("/criarecompensa")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarRecompensa(Recompensa recompensa) throws DatabaseException {
        boolean sucesso = recompensaService.criarRecompensa(recompensa.getNome(), recompensa.getPontosNecessarios(), recompensa.getDescricao());

        if (sucesso) {
            return Response.status(Response.Status.CREATED)
                    .entity("Recompensa criada com sucesso!")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar recompensa")
                    .build();
        }
    }


    // Método para atualizar uma recompensa existente
    @PUT
    @Path("/{id}/attrecomp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarRecompensa(@PathParam("id") int id, Recompensa recompensa) throws DatabaseException {
        recompensa.setId(id); // Define o ID na recompensa
        boolean sucesso = recompensaService.atualizarRecompensa(recompensa);

        if (sucesso) {
            return Response.ok("Recompensa atualizada com sucesso!").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar recompensa")
                    .build();
        }
    }


    // Método para excluir uma recompensa
    @DELETE
    @Path("/{id}/excrecomp")
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirRecompensa(@PathParam("id") int id) throws DatabaseException {
        boolean sucesso = recompensaService.excluirRecompensa(id);

        if (sucesso) {
            return Response.ok("Recompensa excluída com sucesso!").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Recompensa não encontrada")
                    .build();
        }
    }


    // Método para buscar uma recompensa por ID
    @GET
    @Path("/{id}/buscarecomp")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarRecompensaPorId(@PathParam("id") int id) throws DatabaseException {
        Recompensa recompensa = recompensaService.buscarRecompensaPorId(id);

        if (recompensa != null) {
            return Response.ok(recompensa).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Recompensa não encontrada")
                    .build();
        }
    }

    // Método para verificar se o usuário pode resgatar a recompensa
    @GET
    @Path("/poderesgatar/{id}/{pontosUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response podeResgatar(@PathParam("id") int idRecompensa, @PathParam("pontosUsuario") int pontosUsuario) throws DatabaseException {
        boolean podeResgatar = recompensaService.podeResgatar(idRecompensa, pontosUsuario);

        if (podeResgatar) {
            return Response.ok("Você pode resgatar essa recompensa!").build();
        } else {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Você não tem pontos suficientes para resgatar essa recompensa.")
                    .build();
        }
    }



    // Métodos de CRUD para Relatórios

    @GET
    @Path("/relatorios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRelatorios() throws DatabaseException {
        List<Relatorio> relatorios = relatorioService.gerarRelatorios();
        if (relatorios == null || relatorios.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Nenhum relatório encontrado").build();
        }
        return Response.ok(relatorios).build();
    }

    // obter relatório por específico por id
    @GET
    @Path("/relatorio/{id}")
    public Response obterRelatorioPorId(@PathParam("id") int idRelatorio) {
        try {
            Relatorio relatorio = relatorioService.gerarRelatorioPorId(idRelatorio);
            return Response.ok(relatorio).build();
        } catch (RelatorioNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Relatório com ID " + idRelatorio + " não encontrado.")
                    .build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro no servidor ao acessar o banco de dados.")
                    .build();
        }
    }

    // buscar relatorios por categoria
    @GET
    @Path("/categoria/{categoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarRelatoriosPorCategoria(@PathParam("categoria") String categoria) {
        try {
            List<Relatorio> relatorios = relatorioService.buscarRelatoriosPorCategoria(categoria);

            if (relatorios.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nenhum relatório encontrado para a categoria: " + categoria)
                        .build();
            }

            return Response.ok(relatorios).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao acessar o banco de dados: " + e.getMessage())
                    .build();
        }
    }

    // criar novo relatório
    @POST
    @Path("/criarrelatorio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarRelatorio(Relatorio relatorio) throws DatabaseException {
        boolean inserido = relatorioService.inserirRelatorio(relatorio);
        if (inserido) {
            return Response.status(Response.Status.CREATED).entity("Relatório criado com sucesso!").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar o relatório").build();
        }
    }


    // atualiza relatório
    @PUT
    @Path("/attrelatorio/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarRelatorio(@PathParam("id") int id, Relatorio relatorio) throws DatabaseException {
        relatorio.setIdRelatorio(id);  // Garante que o ID seja o do relatório a ser atualizado
        boolean atualizado = relatorioService.atualizarRelatorio(relatorio);
        if (atualizado) {
            return Response.ok("Relatório atualizado com sucesso!").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Relatório não encontrado para atualização").build();
        }
    }


    // deleta relatório
    @DELETE
    @Path("/exclrelatorio/{id}")
    public Response deletarRelatorio(@PathParam("id") int id) throws DatabaseException {
        boolean deletado = relatorioService.deletarRelatorio(id);
        if (deletado) {
            return Response.status(Response.Status.OK).entity("Relatório excluído com sucesso!").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Relatório não encontrado para exclusão").build();
        }
    }





    // Métodos de Usuário

    @GET
    @Path("/listar-user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() throws SQLException {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            return Response.status(Response.Status.OK).entity("Nenhum usuário encontrado.").build();
        }
        return Response.status(Response.Status.OK).entity(usuarios).build();
    }

    @POST
    @Path("/cadastro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarUsuario(Usuario usuario) {
        if (usuarioService.criarUsuario(usuario)) {
            return Response.status(Response.Status.CREATED).entity("Usuário criado com sucesso!").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao salvar usuário. Verifique as informações e tente novamente.").build();
        }
    }

    @PUT
    @Path("/attuser/{id}")  // Aqui o ID do usuário será passado no path da URL
    @Consumes(MediaType.APPLICATION_JSON)  // O corpo da requisição deve ser no formato JSON
    @Produces(MediaType.APPLICATION_JSON)  // A resposta será no formato JSON
    public Response atualizarUsuario(@PathParam("id") int id, Usuario usuario) {
        // Atribuir o ID ao usuário que está sendo atualizado
        usuario.setId(id);

        // Chamar o serviço para atualizar o usuário
        if (usuarioService.atualizarUsuario(usuario)) {
            return Response.status(Response.Status.OK).entity("Usuário atualizado com sucesso!").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar usuário. Verifique as informações e tente novamente.").build();
        }
    }

    // Método para excluir o usuário
    @DELETE
    @Path("/{id}/deletaruser")  // Agora o caminho será id/deletaruser
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirUsuario(@PathParam("id") int id) {
        boolean usuarioExcluido = usuarioService.excluirUsuario(id);

        if (usuarioExcluido) {
            return Response.status(Response.Status.OK).entity("Usuário excluído com sucesso!").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao excluir o usuário. O usuário pode não existir.").build();
        }
    }


}
