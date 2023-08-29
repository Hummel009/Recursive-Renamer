package hummel

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.GridLayout
import java.io.File
import javax.swing.*
import javax.swing.border.EmptyBorder

fun main() {
	EventQueue.invokeLater {
		try {
			for (info in UIManager.getInstalledLookAndFeels()) {
				if ("Windows Classic" == info.name) {
					UIManager.setLookAndFeel(info.className)
					break
				}
			}
			val frame = GUI()
			frame.isVisible = true
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}

class GUI : JFrame() {
	private fun selectPath(pathField: JTextField, dir: Boolean) {
		val fileChooser = JFileChooser()
		if (dir) {
			fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
		}
		val result = fileChooser.showOpenDialog(this)
		if (result == JFileChooser.APPROVE_OPTION) {
			pathField.text = fileChooser.selectedFile.absolutePath
		}
	}

	private fun process(pathField: JTextField, fromField: JTextField, toField: JTextField) {
		if (pathField.text.isEmpty() || fromField.text.isEmpty() || toField.text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Fill the fields", "Error", JOptionPane.ERROR_MESSAGE)
			return
		}

		rename(pathField.text, fromField.text, toField.text)

		JOptionPane.showMessageDialog(
			this, "Rename complete", "Message", JOptionPane.INFORMATION_MESSAGE
		)
	}

	private fun rename(path: String, from: String, to: String) {
		val inputDirectory = File(path)
		val files = inputDirectory.listFiles()
		if (files != null) {
			for (file in files) {
				if (file.isDirectory) {
					rename(file.path, from, to)
				} else {
					val fileName = file.name
					if (fileName.contains(from)) {
						val newFileName = fileName.replace(from, to)
						val newFile = File(file.parent, newFileName)
						file.renameTo(newFile)
					}
				}
			}
		}
	}

	init {
		title = "Recursive Renamer"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 150, 550, 180)

		val contentPanel = JPanel()
		contentPanel.border = EmptyBorder(5, 5, 5, 5)
		contentPanel.layout = BorderLayout(0, 0)
		contentPanel.layout = GridLayout(0, 1, 0, 0)
		contentPane = contentPanel

		val inputPanel = JPanel()
		val inputLabel = JLabel("Input path:")
		inputLabel.preferredSize = Dimension(80, inputLabel.preferredSize.height)
		val inputField = JTextField(24)
		val inputButton = JButton("Select path")
		inputButton.addActionListener { selectPath(inputField, true) }
		inputPanel.add(inputLabel)
		inputPanel.add(inputField)
		inputPanel.add(inputButton)

		val fromPanel = JPanel()
		val fromLabel = JLabel("Replace this:")
		fromLabel.preferredSize = Dimension(80, fromLabel.preferredSize.height)
		val fromField = JTextField(24)
		fromPanel.add(fromLabel)
		fromPanel.add(fromField)

		val toPanel = JPanel()
		val toLabel = JLabel("With this:")
		toLabel.preferredSize = Dimension(80, toLabel.preferredSize.height)
		val toField = JTextField(24)
		toPanel.add(toLabel)
		toPanel.add(toField)

		val processPanel = JPanel()
		val processButton = JButton("Process")
		processButton.addActionListener {
			process(inputField, fromField, toField)
		}
		processPanel.add(processButton)

		contentPanel.add(inputPanel)
		contentPanel.add(fromPanel)
		contentPanel.add(toPanel)
		contentPanel.add(processPanel)

		setLocationRelativeTo(null)
	}
}