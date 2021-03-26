import "./App.css";
import SignIn from "./components/Login";
import SignUp from "./components/Signup";
import Dashboard from "./components/Dashboard/Dashboard";
import { Redirect } from "react-router-dom";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/signin" component={SignIn} />
        <Route path="/signup" component={SignUp} />
        <Route path="/dashboard" component={Dashboard} />
        <Route to="/" component={SignIn} />
        <Redirect from="*" to="/" />
      </Switch>
    </Router>
  );
}

export default App;
