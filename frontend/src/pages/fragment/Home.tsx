import React from 'react'
import homeIcon from '../../assets/home.svg'
import useAuth from '../../hooks/useAuth'
import Fragment from '../../components/Fragment'

export default function Home() {

  return (
    <Fragment
      title='Início'
      Icon={
        <img src={homeIcon}/>
      }
    >
      <div>
        Home
      </div>
    </Fragment>
  )
}
