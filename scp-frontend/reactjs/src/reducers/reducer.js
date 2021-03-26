const initalState = {
    signedIn : false
}

const reducer = (state = initalState, action) => {
    if (action.type === 'SIGN_IN') {
        return {
            ...state,
            signedIn: true
        }
    } else if (action.type === 'SIGN_OUT') {
        return {
            ...state,
            signedIn: false
        }
    }

    return state;
}

export default reducer;