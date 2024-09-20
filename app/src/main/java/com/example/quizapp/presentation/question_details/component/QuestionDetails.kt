package com.example.quizapp.presentation.question_details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.quizapp.data.dto.Question

@Composable
fun QuestionDetails(
    questionDetails: Question?, modifier: Modifier,
    isSelectedItem: (appLanguage: String) -> Boolean,
    onSelectionChangeState: (appLanguage: String) -> Unit
) {


    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
        {
            Text(
                text = questionDetails?.question.toString(),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(24.dp))
            questionDetails?.possibleAnswers?.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .selectable(
                            selected = isSelectedItem(item),
                            onClick = { onSelectionChangeState(item) },
                            role = Role.RadioButton
                        )
                        .padding(top = 12.dp, bottom = 12.dp, end = 28.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = item,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    RadioButton(
                        selected = isSelectedItem(item),
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Black, unselectedColor = Color.Black
                        )
                    )
                }
            }
        }
    }
}