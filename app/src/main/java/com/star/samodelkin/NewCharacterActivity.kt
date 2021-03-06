package com.star.samodelkin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_character.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

private var Bundle.characterData
    get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
    set(value) = putSerializable(CHARACTER_DATA_KEY, value)

class NewCharacterActivity : AppCompatActivity() {

    private lateinit var characterData: CharacterGenerator.CharacterData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_character)

//        characterData = savedInstanceState?.let {
//            it.getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
//        } ?: CharacterGenerator.generate()

        GlobalScope.launch(Dispatchers.Main) {
            characterData = savedInstanceState?.characterData ?: fetchCharacterData()
            displayCharacterData()
        }

        generateButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                characterData = fetchCharacterData()
                displayCharacterData()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

//        outState.putSerializable(CHARACTER_DATA_KEY, characterData)
        outState.characterData = characterData
    }

    private fun displayCharacterData() {
        characterData.run {
            nameTextView.text = name
            raceTextView.text = race
            dexterityTextView.text = dex
            wisdomTextView.text = wis
            strengthTextView.text = str
        }
    }
}
