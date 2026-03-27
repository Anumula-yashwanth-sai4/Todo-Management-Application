import { BrowserRouter, Routes, Route } from "react-router-dom";
import { NavBar } from "./components/NavBar";
import { Home } from "./pages/Home";
import { Add } from "./pages/Add";
import { Lists } from "./pages/Lists";
import { FilterPage } from "./pages/FilterPage";
import { Form } from "./components/Form";
function App() {
   return (
<BrowserRouter>
<NavBar />
<Routes>
<Route path="/" element={<Home />} />
<Route path="/add" element={<Add />} />
<Route path="/lists" element={<Lists />} />
<Route path="/filter" element={<FilterPage />} />
<Route path="/edit/:id" element={<Form />} />
</Routes>
</BrowserRouter>
   );
}
export default App;