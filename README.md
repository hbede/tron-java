# tron-java
A simple multiplayer java game based on the movie TRON.

Unserer Applikatikon wird aus zwei Teilen stehen: Server und Klient.
Die Spielidee stammt aus den Film "TRON", wo "Lichtmotors" versuchen einander eliminieren so, dass einen vor den andere durchgehen zu versuchen muss. Ein Spieler verliert, wenn kollidiert entweder mit selbst, mit dem anderen Spieler, oder mit dem Wand. Wenn die zwei Spieler gleichzeitig (oder frontal) kollidieren, niemand gewinnt (draw). Vielleicht (noch nicht sicher) ein Spiel wird aus mehreren Runden stehen, und der Gewinner wird, wer im Voraus gegebene Anzahl der Runden gewonnen ist (zB. 10).
Weiterhin werden Geschwindigkeit und Fenstergröße des Spiels sich umstellen gelassen.
Bevor das Spiel startet, werden die Farben der Spielern zu umstellen.
Die graphische Spielelemente werden durch SWING verwirklicht, und der Programm wird über ein einfaches Menüsystem verfügen. Frühere Ergebnisse werden lokal gespeichert, und in einem Scoreboard angezeigt.
Mit dem Spiel können zwei Leute spielen mit verschiedene Computers. Die Verbindung den zwei Spielern wird durch einen Server mit UDP verwirklicht. Beim Start ein Spieler kann wählen, ob er/sie ein Host sein möchtet. Der andere Spieler kann sich mit dem Host verbinden. Es ist nötig deswegen, die IP-Adresse von dem Host zu kennen. Der Server speichert die Position und Orientation von beiden Spieler und sendet es ihn regulär um ihre Positionen synchronisieren zu können. Wir wählen UDP, weil es schneller ist und wenn ein Datei nicht ankommt, kann es mithilfe des Servers korrigiert werden.
