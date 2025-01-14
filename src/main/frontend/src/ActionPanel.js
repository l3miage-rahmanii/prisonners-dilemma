import React from "react"

const ActionPanel = ({ onAction }) => {
    return (
        <div>
            <h2>Choix du Joueur</h2>
            <button onClick={() => onAction("coopérer")}>Coopérer</button>
            <button onClick={() => onAction("trahir")}>Trahir</button>
        </div>
    )
}

export default ActionPanel;
