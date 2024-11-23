package org.example.Service;

import org.example.DAO.MetaReducaoDAO;
import org.example.Exception.DatabaseException;
import org.example.Exception.MetaReducaoException;
import org.example.Model.MetaReducao;

import java.sql.SQLException;
import java.util.List;

public class MetaReducaoService {
    private MetaReducaoDAO metaReducaoDAO;

    public MetaReducaoService(){
        metaReducaoDAO = new MetaReducaoDAO();
    }

    // Método para criar meta de redução
    public boolean criarMeta(double metaEmissao) throws DatabaseException {
        MetaReducao meta = new MetaReducao(metaEmissao);
        try {
            return metaReducaoDAO.criarMeta(meta);
        } catch (DatabaseException e) {
            System.err.println("Erro ao criar meta de redução: " + e.getMessage());
            throw new DatabaseException("Erro ao criar meta de redução");
        }
    }

    public void atualizarMeta(MetaReducao meta) throws DatabaseException {
        // Verifique os valores antes de chamar o DAO
        if (meta.getNome() == null || meta.getDescricao() == null) {
            throw new IllegalArgumentException("Nome ou descrição não podem ser nulos.");
        }

        // Log para depuração
        System.out.println("Camada de Serviço - Nome: " + meta.getNome());
        System.out.println("Camada de Serviço - Descrição: " + meta.getDescricao());

        // Chama o DAO para atualizar a meta
        MetaReducaoDAO dao = new MetaReducaoDAO();
        dao.atualizarMeta(meta);
    }



    // Método para verificar se a meta foi atingida
    public boolean verificarMetaAtingida(int idMeta) throws MetaReducaoException, DatabaseException {
        try {
            MetaReducao meta = metaReducaoDAO.buscarMetaPorId(idMeta);
            if (meta == null) {
                throw new MetaReducaoException("Meta não encontrada");
            }

            // Adiciona logs para verificar os valores
            System.out.println("Meta: " + meta.getMetaEmissao());
            System.out.println("Progresso Atual: " + meta.getProgressoAtual());

            return meta.getProgressoAtual() >= meta.getMetaEmissao();
        } catch (DatabaseException e) {
            System.err.println("Erro ao verificar meta: " + e.getMessage());
            throw new DatabaseException("Erro ao acessar dados da meta");
        }
    }

    // Método para salvar meta de redução
    public boolean salvarMetaReducao(MetaReducao metaReducao) throws DatabaseException {
        try {
            return metaReducaoDAO.criarMeta(metaReducao);
        } catch (DatabaseException e) {
            System.err.println("Erro ao salvar meta de redução: " + e.getMessage());
            throw new DatabaseException("Erro ao salvar meta de redução");
        }
    }

    // Método para listar todas as metas de redução
    public List<MetaReducao> listarMetaReducao() throws DatabaseException {
        try {
            return metaReducaoDAO.listarTodasMetas();
        } catch (DatabaseException e) {
            System.err.println("Erro ao listar metas de redução: " + e.getMessage());
            throw new DatabaseException("Erro ao listar metas de redução");
        }
    }


}
