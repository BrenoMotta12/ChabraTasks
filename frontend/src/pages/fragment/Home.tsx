import React from 'react'
import homeIcon from '../assets/home.svg'
import useAuth from '../../hooks/useAuth'
import Fragment from '../../components/fragment'

export default function Home() {

  return (
    <Fragment
      title='Início'
      icon='src/assets/home.svg'
    >
      <div>
        Home
      </div>
    </Fragment>
  )
}
