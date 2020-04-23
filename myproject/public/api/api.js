import axios from 'axios';
let base = 'http://127.0.0.1:8088';
//let base = 'http://192.168.1.100:8088';

export default base
export const requestLogin = params => {
    return axios.post(`${base}/auth/toLogin`, params).then(res => res.data).catch(error => console.log(error));};