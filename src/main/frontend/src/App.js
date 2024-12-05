import React, { useState } from "react"
import Settings from "./Settings"
import GameBoard from "./GameBoard"
import ActionPanel from "./ActionPanel"

const App = () => {
  const [gameStarted, setGameStarted] = useState(false)
  const [rounds, setRounds] = useState(0)
  const [results, setResults] = useState([])

  const startGame = ({ rounds }) => {
    setRounds(rounds)
    setGameStarted(true)
  }

  const handleAction = (action) => {
    console.log("Joueur a choisi :", action)
    // Ajouter une logique pour envoyer les choix au backend et mettre à jour les résultats
  }

  return (
      <div>
        {!gameStarted && <Settings onStartGame={startGame} />}
        {gameStarted && (
            <>
              <GameBoard rounds={rounds} results={results} />
              <ActionPanel onAction={handleAction} />
            </>
        )}
      </div>
  )
}

export default App
