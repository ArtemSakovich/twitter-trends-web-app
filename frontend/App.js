import React, { Component } from 'react';
import './App.css'; /* optional for styling like the :hover pseudo-class */
import USAMap from "react-usa-map";
import axios from "axios";

class App extends Component {
    /* mandatory */

    constructor(props) {
        super(props)
        this.state = {
            states: {},
            topic: null,
            message: null
        }
        this.refreshStates = this.refreshStates.bind(this)
    }

    componentDidMount() {
        this.refreshStates()
    }

    refreshStatesButtonClick(tweetTopic, e) {
        e.preventDefault();
            this.state.topic = tweetTopic;
            this.refreshStates();
    }

    getStateColor(stateWeight, amountOfTweets) {
        if (stateWeight <= -0.5) {
            return "#42A996";
        }
        else if (stateWeight > -0.5 && stateWeight < 0){
            return "#84CCFC";
        }
        else if (stateWeight === 0 && amountOfTweets === 1) {
            return "#FFCCE5";
        }
        else if (stateWeight === 0 && amountOfTweets !== 1) {
            return "#B8B8B8";
        }
        else if (stateWeight > 0 && stateWeight < 0.5) {
            return "#FFB266";
        }
        else if (stateWeight >= 0.5) {
            return "#FF6666";
        }
    }

    refreshStates() {
        if (this.state.topic !== null) {
            axios.get('http://localhost:8080/topicTweets/' + this.state.topic)
                .then(
                    response => {
                        let statesObj = {};
                        console.log(response.data);
                        response.data.map(
                            state => {
                                statesObj[state.stateName] = {};
                                statesObj[state.stateName].fill = this.getStateColor(state.stateWeight,
                                    state.amountOfTweets);
                            }
                        )
                        this.setState({states: statesObj, topic: this.state.topic})
                    }
                )
        }
    }

    mapHandler = (event) => {
        alert(event.target.dataset.name);
    };

    render() {
        return (
            <div>
                <div className={"buttons"}>
                <button onClick={(e) => this.refreshStatesButtonClick('texas', e)}>Texas</button>
                <button onClick={(e) => this.refreshStatesButtonClick('cali', e)}>California</button>
                <button onClick={(e) => this.refreshStatesButtonClick('family', e)}>Family</button>
                <button onClick={(e) => this.refreshStatesButtonClick('football', e)}>Football</button>
                <button onClick={(e) => this.refreshStatesButtonClick('highSchool', e)}>High School</button>
                <button onClick={(e) => this.refreshStatesButtonClick('movie', e)}>Movie</button>
                <button onClick={(e) => this.refreshStatesButtonClick('shopping', e)}>Shopping</button>
                <button onClick={(e) => this.refreshStatesButtonClick('snow', e)}>Snow</button>
                <button onClick={(e) => this.refreshStatesButtonClick('weekend', e)}>Weekend</button>
                </div>
                <img src={"/color_legend.png"}/>
                <p className={"map"}><USAMap customize={this.state.states} onClick={this.mapHandler}/></p>
            </div>
        );
    }
}

export default App;