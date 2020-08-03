import axios from 'axios'

export default axios.create({
    baseURL: "http://i3a606.p.ssafy.io:8090/api",
    headers:{
        "Content-type": "application/json",
    }
});