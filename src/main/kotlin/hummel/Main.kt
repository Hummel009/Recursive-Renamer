package hummel

import java.io.File

fun renameFiles(folder: File) {
	val files = folder.listFiles()

	if (files != null) {
		for (file in files) {
			if (file.isDirectory) {
				renameFiles(file)
			} else {
				val fileName = file.name
				if (fileName.contains("khommurat")) {
					val newFileName = fileName.replace("khommurat", "hoarmurath")
					val newFile = File(file.parent, newFileName)
					file.renameTo(newFile)
					println("Renamed file: $fileName -> $newFileName")
				}
			}
		}
	}
}

fun main() {
	val rootFolder = File("D:\\Eclipse\\Minecraft\\Legendary-Item")
	renameFiles(rootFolder)
	println("File renaming completed.")
}