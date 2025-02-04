import React, { useEffect, useState } from 'react'
import Fragment from '../../components/Fragment'
import { Outlet, useParams } from 'react-router-dom'
import { Space } from '../../models/Space'
import useAuth from '../../hooks/useAuth'
import { List } from '../../models/List'
import { Api, getHeadersAndParams, getHeadersAuthorization } from '../../services/Api'
import listIcon from '../../assets/list.svg'
import FragmentNavBar from '../../components/FragmentNavBar'

export default function ListFragment() {

    const [space, setSpace] = useState<Space>()
    const [list, setList] = useState<List>()
    const { id } = useParams<{ id: string }>()
    const { user } = useAuth()
   
    
    useEffect(() => {
        if (user?.token) {
            fetchList();
        }
    }, [user?.token, id]);

    useEffect(() => {
        if (list?.spaceId) {
            fetchSpace();
        }
    }, [list]);

    const fetchList = async () => {
        try {
            const response = await Api.get(`/list/${id}`, getHeadersAuthorization(user?.token));
            setList(response.data);
        } catch (error) {
            console.error("Erro ao buscar lista:", error);
        }
    };

    const fetchSpace = async () => {
        try {
            const response = await Api.get(`/space/${list?.spaceId}`, getHeadersAuthorization(user?.token));
            setSpace(response.data);
        } catch (error) {
            console.error("Erro ao buscar espaço:", error);
        }
    };

    return (
        <Fragment
            Icon={
                <h1 className="bg-[#02A85C] text-white font-medium text-2xl rounded-xl h-8 w-8 flex items-center justify-center">
                    {space?.name?.charAt(0).toUpperCase()}
                </h1>
            }
            title={`${space?.name} > ${list?.name}`}
        >
            <FragmentNavBar
                id={id}
            />

            <Outlet />

        </Fragment>
    )
}
