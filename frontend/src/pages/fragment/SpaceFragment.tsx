import React, { useEffect, useState } from 'react'
import Fragment from '../../components/Fragment'
import { Space } from '../../models/Space'
import { List } from '../../models/List'
import { useParams } from 'react-router-dom'
import { Api, getHeadersAndParams, getHeadersAuthorization } from '../../services/Api'
import useAuth from '../../hooks/useAuth'

export default function SpaceFragment() {

    const [space, setSpace] = useState<Space>()
    const { id } = useParams<{ id: string }>()
    const { user } = useAuth()
        
    useEffect (() => {
        if(!user?.token) return
        if(!id) return
        fetchSpace(user.token, id)
    }, [id])

    const fetchSpace = async (token: string, id: string) => {
        try {
            const header = {
                headers: {"Content-Type": "application/json", Authorization: `Bearer ${token}`}
            }
            const response = await Api.get(`/space/${id}`, header)
            setSpace(response.data)
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <Fragment
            title={space?.name ? space?.name : ''}
            Icon={
                <h1 className="text-white font-medium text-2xl rounded-xl h-8 w-8 flex items-center justify-center"
                    style={{backgroundColor: space?.color ? space?.color : "#7F7F7F"}}
                >
                    {space?.name?.charAt(0).toUpperCase()}
                </h1>
            }
        >
            <div>
                -
                
            </div>
        </Fragment>
    )
}
