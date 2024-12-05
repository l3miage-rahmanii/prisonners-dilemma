import React from "react"

const GameBoard = ({ rounds, results }) => {
    return (
        <div>
            <h2>Résultats</h2>
            <table>
                <thead>
                <tr>
                    <th>Tour</th>
                    <th>Joueur 1</th>
                    <th>Joueur 2</th>
                    <th>Score</th>
                </tr>
                </thead>
                <tbody>
                {results.map((result, index) => (
                    <tr key={index}>
                        <td>{index + 1}</td>
                        <td>{result.player1}</td>
                        <td>{result.player2}</td>
                        <td>{result.score}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
}

export default GameBoard
