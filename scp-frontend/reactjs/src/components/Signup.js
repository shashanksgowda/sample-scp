import React from "react";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import Link from "@material-ui/core/Link";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {"Copyright © "}
      <Link color="inherit" href="https://material-ui.com/">
        Your Website
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(3),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

const SignUp = () => {
  const initialUserDetails = {
    firstName: "",
    lastName: "",
    username: "",
    password: "",
  };
  const classes = useStyles();

  const [userDetail, setUserDetail] = React.useState(initialUserDetails);

  const [error, setError] = React.useState({
    firstNameError: "",
    usernameError: "",
    passwordError: "",
  });

  const [signUpComplete, setSignupComplete] = React.useState(false);

  const handleChange = (e) => {
    setUserDetail({
      ...userDetail,
      [e.target.name]: e.target.value,
    });
  };

  React.useEffect(() => {
    setSignupComplete(false);
    console.log(error);
  }, [error]);

  const validateSignup = () => {
    if (!userDetail.firstName) {
      setError({
        firstNameError: "First Name cannot be empty",
      });
    }
    if (!userDetail.username) {
      setError({
        ...error,
        usernameError: "username cannot be empty",
      });
    }
    if (!userDetail.password) {
      setError({
        ...error,
        passwordError: "password cannot be empty",
      });
    }
    if (error.firstNameError || error.passwordError || error.usernameError) {
      console.log("Not proceeded for user creation");
      return;
    } else {
      const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          firstName: userDetail.firstName,
          lastName: userDetail.lastName,
          username: userDetail.username,
          password: userDetail.password,
        }),
      };
      fetch("/api/user/signup", requestOptions)
        .then((response) => setSignupComplete(true))
        .catch((err) => console.log("error occured during fetch"));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    validateSignup();
  };

  if (signUpComplete) {
    return <SignUpComplete />;
  }
  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <form className={classes.form} noValidate onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                autoComplete="fname"
                name="firstName"
                variant="outlined"
                required
                fullWidth
                id="firstName"
                label="First Name"
                autoFocus
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                variant="outlined"
                required
                fullWidth
                id="lastName"
                label="Last Name"
                name="lastName"
                autoComplete="lname"
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                id="username"
                label="Username"
                name="username"
                autoComplete="username"
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
                onChange={handleChange}
              />
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Sign Up
          </Button>
          <Grid container justify="flex-end">
            <Grid item>
              <Link href="/signin" variant="body2">
                Already have an account? Sign in
              </Link>
            </Grid>
          </Grid>
        </form>
      </div>
      <Box mt={5}>
        <Copyright />
      </Box>
    </Container>
  );
};

const SignUpComplete = () => {
  return (
    <Container component="main" maxWidth="xs">
      <h1>You have been successfully registered!</h1>
      <Grid container>
            <Grid item>
              <Link href="/signin" variant="body2">
                Click Here to Sign In!
              </Link>
            </Grid>
          </Grid>
    </Container>
  );
};

export default SignUp;
