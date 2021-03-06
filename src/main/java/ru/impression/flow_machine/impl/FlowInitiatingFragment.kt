package ru.impression.flow_machine.impl

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import ru.impression.flow_machine.Flow
import ru.impression.flow_machine.FlowInitiator
import ru.impression.flow_machine.FlowPerformer

abstract class FlowInitiatingFragment<F : Flow<*>>(final override val flowClass: Class<F>) :
    Fragment(), FlowInitiator<F>, FlowPerformer<F> {

    open val eventEnrichers: List<FlowPerformer<F>> = emptyList()

    final override fun attachToFlow() = super.attachToFlow()

    final override fun detachFromFlow() = super.detachFromFlow()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startFlow()
        attachToFlow()
    }

    final override fun eventOccurred(event: Flow.Event) {
        eventEnrichers.forEach { it.enrichEvent(event) }
        super.eventOccurred(event)
    }

    override fun onDestroyView() {
        detachFromFlow()
        finishFlow()
        super.onDestroyView()
    }
}