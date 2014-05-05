# Setup

## Gitbook (Optional)

[Gitbook](http://www.gitbook.io/) ist eine Konvention, mittels Markdown Bücher zu erstellen, welche dann als Webseite, pdf oder als eBook verbreitet werden können. Des Weiteren gibt es zum Erstellen und Bearbeiten eine [Applikation](https://github.com/GitbookIO/editor/releases) für Windows, Mac und Linux.

Diese Anleitung ist als Gitbook geschrieben und kann

## Leiningen

[Leiningen](http://leiningen.org/) ist ein Kommandozeilen-Taskrunner zur Verwaltung von Clojure Projekten. Darunter fallen Build-Management, Dependency Resolution sowie die Möglichkeit, eigene Tasks zu beschreiben - quasi Cake für Clojure. Für Windows-Nutzer gibt es den [leiningen-win-installer](http://leiningen-win-installer.djpowell.net/), welcher einen grafischen Installationsdialog benutzt.

Nach Abschluss der Installation ist die Datei ``lein.bat`` zur PATH Variablen hinzugefügt worden, welche Leiningen startet. Des weiteren wurde LEIN_JAVA_CMD auf das ausgewählte JDK gesetzt sowie die :java-cmd property im Benutzerprofil der ``profiles.clj`` gesetzt.