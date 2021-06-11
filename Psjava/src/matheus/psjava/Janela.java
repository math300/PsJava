package matheus.psjava;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Janela {

	public static void main(String[] args) {
		DadosMissaoDao dao = new DadosMissaoDao();
		JFrame janela = new JFrame("Matheus Miguel RM82276 3ECR PS");

		JTabbedPane abas = new JTabbedPane();

		JPanel cadastro = new JPanel(new BorderLayout());
		JPanel camposCadastro = new JPanel(new GridLayout(8, 1));

		camposCadastro.add(new JLabel("Id"));
		JTextField campoId = new JTextField();
		campoId.setEditable(false);
		camposCadastro.add(campoId);

		camposCadastro.add(new JLabel("Pressão Atm"));
		JTextField pressaoAtm = new JTextField();
		camposCadastro.add(pressaoAtm);
		
		camposCadastro.add(new JLabel("Temp Min"));
		JTextField tempMin = new JTextField();
		camposCadastro.add(tempMin);
		
		camposCadastro.add(new JLabel("Temp Máx"));
		JTextField tempMax = new JTextField();
		camposCadastro.add(tempMax);



		cadastro.add(camposCadastro, BorderLayout.CENTER);

		JPanel buttons = new JPanel();

		JButton salvar = new JButton("Salvar");
		salvar.addActionListener(action -> {
			DadosMissao dados = new DadosMissao();
			dados.setPressaoAtm(Double.parseDouble(pressaoAtm.getText()));
			dados.setTempMin(Double.parseDouble(tempMin.getText()));
			dados.setTempMax(Double.parseDouble(tempMax.getText()));
			if (campoId.getText().isBlank()) {
				dao.salvar(dados);
			} else {
				dados.setId(Integer.parseInt(campoId.getText()));
				dao.atualizar(dados);
			}
			campoId.setText("");
			pressaoAtm.setText("");
			tempMin.setText("");
			tempMax.setText("");
		});
		buttons.add(salvar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(action -> {
			campoId.setText("");
			pressaoAtm.setText("");
			tempMin.setText("");
			tempMax.setText("");
		});
		buttons.add(cancelar);
		cadastro.add(buttons, BorderLayout.SOUTH);

		abas.add("Cadastro", cadastro);

		JPanel lista = new JPanel(new BorderLayout());
		JPanel tabela = new JPanel(new GridLayout(1, 1));

		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("ID");
		modelo.addColumn("Pressão Atm");
		modelo.addColumn("Temp Min");
		modelo.addColumn("Temp Máx");
		dao.getList().forEach(dado -> {
			modelo.addRow(new Object[] { 
					dado.getId(), 
					dado.getPressaoAtm(),
					dado.getTempMin(), 
					dado.getTempMax(),
				});
			
		});
		JTable tabelaJ = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tabelaJ);
		tabela.add(scroll);
		lista.add(tabela, BorderLayout.CENTER);

		JPanel botoesLista = new JPanel();

		JButton editar = new JButton("Editar");
		editar.addActionListener(action -> {
			int linha = tabelaJ.getSelectedRow();
			if (linha >= 0) {
				abas.setSelectedIndex(0);
				campoId.setText(String.valueOf(tabelaJ.getValueAt(linha, 0)));
				pressaoAtm.setText(String.valueOf(tabelaJ.getValueAt(linha, 1)));
				tempMin.setText(String.valueOf(tabelaJ.getValueAt(linha, 2)));
				tempMax.setText(String.valueOf(tabelaJ.getValueAt(linha, 3)));
			} else {
				JOptionPane.showMessageDialog(null, "Selecione uma linha!", "Erro", JOptionPane.ERROR_MESSAGE);
			}

		});
		JButton deletar = new JButton("Deletar");
		deletar.addActionListener(action -> {

			int linha = tabelaJ.getSelectedRow();
			if (linha >= 0) {
				dao.remover(Integer.parseInt(String.valueOf(tabelaJ.getValueAt(linha, 0))));

			} else {
				JOptionPane.showMessageDialog(null, "Selecione uma linha!", "Erro", JOptionPane.ERROR_MESSAGE);
			}

		});
		botoesLista.add(deletar);
		botoesLista.add(editar);
		lista.add(botoesLista, BorderLayout.SOUTH);

		abas.add("Lista", lista);

		janela.add(abas);
		janela.setSize(800, 600);
		janela.setVisible(true);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
