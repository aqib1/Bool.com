import { POST, PUT, JSON_CONTENT_TYPE } from "./consts.js";

const BASE_URL = `${window.location.origin}/game`;

const registerEventForPits = () => {
    const pits = Array.from(document.getElementsByClassName("pit"));
    pits.forEach(pit => pit.addEventListener("click", pick));
}

const pick = async e => {
     const pitIndex = e.srcElement.id.replace(/^\D+/g, "");
     const moveUrl = `${BASE_URL}/move`;
     const currentPlayerName = document.getElementById("player-turn").innerText;
     const moveRequest = {
         uuid: gameResponseData.uuid,
         player_type: getPlayerTypeByName(currentPlayerName),
         index: pitIndex
       };

     const moveResponse = await fetch(moveUrl, {
        headers: {
              "Content-Type": JSON_CONTENT_TYPE
            },
            method: PUT,
            body: JSON.stringify(moveRequest)
     });

     const data = await moveResponse.json();

     if(moveResponse.ok) {
        populateGame(data);
        if (data.status == "END") {
             alert(`Winner: ${data.winnerPlayer}`);
        }
     } else {
        alert(data.detailedMessage);
     }
};

const getPlayerTypeByName = playerName => {
  const firstPlayer = document.getElementById("player1-name").innerText;
  return playerName === firstPlayer ? "FIRST" : "SECOND";
};

const getPlayerNameByType = (playerType, data) => {
  return playerType === "FIRST" ? data.firstPlayer.name : data.secondPlayer.name;
};


const populateGame = data => {
  const gameStatusElement = document.getElementById("game-status");
  const totalTurnElement = document.getElementById("total-turn");
  const currentTurnElement = document.getElementById("player-turn");
  gameStatusElement.innerText = data.status;
  totalTurnElement.innerText = data.totalTurn;
  currentTurnElement.innerText = getPlayerNameByType(data.playerTurn, data);
  populateBoard(data.board);
};

const populateBoard = board => {
    board.forEach((number, i) => {
        const pitElement = document.getElementById(`pit${i}`);
        pitElement.innerText = number;
    });
};

const onLoad = async () => {
    registerEventForPits();
    populateGame(gameResponseData);
}


window.onload = onLoad;