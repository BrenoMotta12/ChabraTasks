import axios from 'axios'

export const Api = axios.create({
    baseURL: "http://localhost:8080/"
})

export function getHeaders() {
    return {headers: {"Content-Type": "application/json"}}
}

export function getHeadersAuthorization(token: string | undefined) {
    return {headers: {"Content-Type": "application/json", Authorization: `Bearer ${token}`}}
}

export function getHeadersBodyAuthorization(token: string | undefined) {
    
    return {headers: {"Content-Type": "application/json", Authorization: `Bearer ${token}`}}
}

export function getAuthorization(token: string | undefined) {
    
    return {headers: {Authorization: `Bearer ${token}`}}
}

export function getHeadersAndParams(search: string, token: string | undefined) {
    
    return {headers: {Authorization: `Bearer ${token}`}, params: {search: search}}
      
    
}
