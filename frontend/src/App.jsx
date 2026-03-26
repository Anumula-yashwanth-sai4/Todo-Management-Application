import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import { NavBar } from './NavBar'
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Home } from './components/Home'


function App() {

  return (
    <>
    <BrowserRouter>
      <NavBar/>
      <div className="pt-5">   {/* pushes content below navbar */}
    <Routes>
      <Route path="/" element={<Home />} />
    </Routes>
  </div>

    </BrowserRouter>
  
    </>
  )
}

export default App
