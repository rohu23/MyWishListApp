package com.rtee.mywishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rtee.mywishlistapp.R.string.Update_Wish
import com.rtee.mywishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewmodel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember{
        mutableStateOf("")
    }
    
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    if (id != 0L){
        val wish = viewmodel.getWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewmodel.wishTitleState = wish.value.title
        viewmodel.wishDescriptionState = wish.value.description
    }else{
        viewmodel.wishTitleState = ""
        viewmodel.wishDescriptionState = ""
    }


    Scaffold(
        topBar = {
            AppBarView(
                title =
                if (id != 0L) stringResource(R.string.Update_Wish)
                else stringResource(R.string.Add_Wish)
            ) {
                navController.navigateUp()
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Title",
                value = viewmodel.wishTitleState,
                onValueChange = {
                    viewmodel.onWishTitleChanged(it.toString())
                })
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Description",
                value = viewmodel.wishDescriptionState,
                onValueChange = {
                    viewmodel.onWishDescriptionChanged(it.toString())
                })

            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if (viewmodel.wishTitleState.isNotEmpty() &&
                    viewmodel.wishDescriptionState.isNotEmpty()){
                    if (id != 0L){
                        viewmodel.updateWish(
                            Wish(
                                id = id,
                                title = viewmodel.wishTitleState.trim(),
                                description = viewmodel.wishDescriptionState.trim()
                            ))
                    }else{
                        viewmodel.addWish(
                            Wish(
                                title = viewmodel.wishTitleState.trim(),
                                description = viewmodel.wishDescriptionState.trim())
                        )
                            snackMessage.value = "Wish has been created"
                    }
                }else{
                    snackMessage.value = "Please fill fields to create wish"
                }
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }
            }) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.Update_Wish)
                    else stringResource(id = R.string.Add_Wish),
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
        }

    }

}
@Composable
fun WishTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(

            textColor = Color.Black,
            focusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black)
        )
    )
}


@Preview
@Composable
fun WishTextFieldPreview() {
    WishTextField(label = "text", value = "text", onValueChange = {})
}
