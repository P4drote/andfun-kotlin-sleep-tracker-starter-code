/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 */
class SleepTrackerFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

        //Creo un enlace a la application, lanzara una excepción si el valor es nulo
        val application = requireNotNull(this.activity).application

        //Creo una referencia a la base de datos a traves de la referencia DAO
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        //Creamos una instancia al modelo de vista factory de la aplicación
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        //Ahora le enviamos la base de datos y la aplicación al proveedor de vista y le pedimos un modelo de vista
        //de la aplicación
        val sleepTrackerViewModel =
                    ViewModelProviders.of(
                        this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        //enlazo el objeto al diseño
        binding.sleepTrackerViewModel = sleepTrackerViewModel

        //especifico que la actual activity es el propietario del ciclo de vida
        binding.setLifecycleOwner(this)


        return binding.root
    }
}
