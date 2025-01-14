import React, { useState } from "react"

const Settings = ({ onStartGame }) => {
    const [rounds, setRounds] = useState(10)
    const [strategy, setStrategy] = useState("donnant-donnant")

    const handleStart = () => {
        onStartGame({ rounds, strategy })
    }

    return (
        <div>
            <h2>Configurer la Partie</h2>
            <label>
                Nombre de tours :
                <input
                    type="number"
                    value={rounds}
                    onChange={(e) => setRounds(e.target.value)}
                />
            </label>
            <br />
            <label>
                Stratégie :
                <select
                    value={strategy}
                    onChange={(e) => setStrategy(e.target.value)}
                >
                    <option value="donnant-donnant">Donnant Donnant</option>
                    <option value="aléatoire">Aléatoire</option>
                    <option value="toujours-coopérer">Toujours Coopérer</option>
                </select>
            </label>
            <br />
            <button onClick={handleStart}>Démarrer la Partie</button>
        </div>
    )
}

export default Settings
