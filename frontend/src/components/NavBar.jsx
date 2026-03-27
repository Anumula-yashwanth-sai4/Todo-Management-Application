import "bootstrap/dist/css/bootstrap.min.css";
import { NavLink } from "react-router-dom";

export function NavBar() {
  return (
   // <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
   <nav
  className="navbar navbar-expand-lg navbar-dark"
  style={{ backgroundColor: "#d382ef" }}   // dark purple
>
      <div className="container-fluid">
        <span className="navbar-brand">Taskarooo</span>
        <div className="collapse navbar-collapse">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <NavLink
                to="/"
                end
                className={({ isActive }) =>
                  isActive ? "nav-link active fw-bold text-white" : "nav-link text-white"
                }
              >
                Home
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                to="/add"
                className={({ isActive }) =>
                  isActive ? "nav-link active fw-bold text-white" : "nav-link text-white"
                }
              >
                Add
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                to="/lists"
                className={({ isActive }) =>
                  isActive ? "nav-link active fw-bold text-white" : "nav-link text-white"
                }
              >
                Lists
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink
                to="/filter"
                className={({ isActive }) =>
                  isActive ? "nav-link active fw-bold text-white" : "nav-link text-white"
                }
              >
                Filter
              </NavLink>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}